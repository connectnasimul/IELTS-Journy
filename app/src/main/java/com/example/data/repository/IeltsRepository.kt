package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.dao.AppDao
import com.example.data.local.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class IeltsRepository(private val dao: AppDao) {

    suspend fun ensureInitialized() = withContext(Dispatchers.IO) {
        val prefs = dao.getUserPreferencesSync()
        if (prefs == null) {
            AppDatabase.populateDatabase(dao)
        } else {
            // Ensure curriculum is kept updated with all audited tasks without modifying user progress
            dao.insertPhases(com.example.data.local.InitialCurriculum.phases)
            dao.insertDays(com.example.data.local.InitialCurriculum.days)
            dao.insertTasks(com.example.data.local.InitialCurriculum.tasks)
            dao.insertVocabularyList(com.example.data.local.InitialCurriculum.vocabularyList)
            dao.insertAssessments(com.example.data.local.InitialCurriculum.weeklyAssessments)
        }
    }

    // --- Preferences ---
    fun getUserPreferences(): Flow<UserPreferencesEntity?> = dao.getUserPreferences()
    suspend fun updateUserPreferences(prefs: UserPreferencesEntity) = withContext(Dispatchers.IO) {
        dao.insertUserPreferences(prefs)
    }

    // --- Curriculum ---
    fun getAllPhases(): Flow<List<StudyPhaseEntity>> = dao.getAllPhases()
    fun getAllDays(): Flow<List<StudyDayEntity>> = dao.getAllDays()
    fun getAllTasks(): Flow<List<StudyTaskEntity>> = dao.getAllTasks()

    // --- Task Progress (Separated & dynamic) ---
    fun getAllTaskProgress(): Flow<List<TaskProgressEntity>> = dao.getAllTaskProgress()

    suspend fun toggleTask(taskId: String, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        val allTasks = dao.getAllTasksSync()
        val currentTask = allTasks.find { it.id == taskId }

        if (isCompleted) {
            dao.setTaskProgress(
                TaskProgressEntity(
                    taskId = taskId,
                    isCompleted = true,
                    completedAt = System.currentTimeMillis()
                )
            )
            // Complete all child subtasks if parent was checked
            val children = allTasks.filter { it.parentTaskId == taskId }
            children.forEach { child ->
                dao.setTaskProgress(
                    TaskProgressEntity(
                        taskId = child.id,
                        isCompleted = true,
                        completedAt = System.currentTimeMillis()
                    )
                )
            }
            // Check if all siblings are now completed to complete parent
            if (currentTask?.parentTaskId != null) {
                val parentId = currentTask.parentTaskId
                val siblings = allTasks.filter { it.parentTaskId == parentId }
                val completedMap = dao.getAllTaskProgressSync().filter { it.isCompleted }.map { it.taskId }.toSet()
                if (siblings.all { it.id == taskId || completedMap.contains(it.id) }) {
                    dao.setTaskProgress(
                        TaskProgressEntity(
                            taskId = parentId,
                            isCompleted = true,
                            completedAt = System.currentTimeMillis()
                        )
                    )
                }
            }
        } else {
            dao.deleteTaskProgress(taskId)
            // Uncheck children if parent was unchecked
            val children = allTasks.filter { it.parentTaskId == taskId }
            children.forEach { child ->
                dao.deleteTaskProgress(child.id)
            }
            // Uncheck parent if child was unchecked
            if (currentTask?.parentTaskId != null) {
                dao.deleteTaskProgress(currentTask.parentTaskId)
            }
        }
    }

    suspend fun resetAllTasks() = withContext(Dispatchers.IO) {
        dao.resetAllTaskProgress()
    }

    suspend fun resetPhaseTasks(phaseId: Int) = withContext(Dispatchers.IO) {
        val tasks = dao.getTasksForPhaseSync(phaseId)
        val taskIds = tasks.map { it.id }
        if (taskIds.isNotEmpty()) {
            dao.resetTaskProgressForTasks(taskIds)
        }
    }

    suspend fun resetDayTasks(dayId: Int) = withContext(Dispatchers.IO) {
        val tasks = dao.getTasksForDaySync(dayId)
        val taskIds = tasks.map { it.id }
        if (taskIds.isNotEmpty()) {
            dao.resetTaskProgressForTasks(taskIds)
        }
    }

    // --- Journal ---
    fun getAllJournalEntries(): Flow<List<JournalEntryEntity>> = dao.getAllJournalEntries()

    suspend fun saveJournalEntry(entry: JournalEntryEntity): Long = withContext(Dispatchers.IO) {
        if (entry.id == 0L) {
            dao.insertJournalEntry(entry)
        } else {
            dao.updateJournalEntry(entry)
            entry.id
        }
    }

    suspend fun deleteJournalEntry(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteJournalEntry(id)
    }

    suspend fun deleteAllJournalEntries() = withContext(Dispatchers.IO) {
        dao.deleteAllJournalEntries()
    }

    // --- Study Sessions & Focus Timer ---
    fun getAllStudySessions(): Flow<List<StudySessionEntity>> = dao.getAllStudySessions()

    suspend fun addStudySession(session: StudySessionEntity): Long = withContext(Dispatchers.IO) {
        val existingSessions = dao.getAllStudySessionsSync()
        val isDuplicate = existingSessions.any { existing ->
            existing.date == session.date &&
            existing.section == session.section &&
            existing.actualDurationMinutes == session.actualDurationMinutes &&
            kotlin.math.abs(existing.startTimestamp - session.startTimestamp) < 5_000L
        }
        if (isDuplicate) {
            -1L
        } else {
            dao.insertStudySession(session)
        }
    }

    suspend fun deleteStudySession(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteStudySession(id)
    }

    fun getAllFocusRecords(): Flow<List<FocusTimerRecordEntity>> = dao.getAllFocusRecords()

    suspend fun addFocusRecord(record: FocusTimerRecordEntity): Long = withContext(Dispatchers.IO) {
        val existingRecords = dao.getAllFocusRecordsSync()
        val isDuplicate = existingRecords.any { existing ->
            existing.date == record.date &&
            existing.mode == record.mode &&
            existing.actualMinutes == record.actualMinutes &&
            kotlin.math.abs(existing.timestamp - record.timestamp) < 5_000L
        }
        if (isDuplicate) {
            -1L
        } else {
            dao.insertFocusRecord(record)
        }
    }

    suspend fun deleteAllFocusRecords() = withContext(Dispatchers.IO) {
        dao.deleteAllFocusRecords()
    }

    // --- Vocabulary ---
    fun getAllVocabulary(): Flow<List<VocabularyEntryEntity>> = dao.getAllVocabulary()

    suspend fun saveVocabulary(item: VocabularyEntryEntity): Long = withContext(Dispatchers.IO) {
        if (item.id == 0L) {
            dao.insertVocabulary(item)
        } else {
            dao.updateVocabulary(item)
            item.id
        }
    }

    suspend fun deleteVocabulary(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteVocabulary(id)
    }

    // --- Practice Tests ---
    fun getAllPracticeTests(): Flow<List<PracticeTestEntity>> = dao.getAllPracticeTests()

    suspend fun addPracticeTest(test: PracticeTestEntity): Long = withContext(Dispatchers.IO) {
        dao.insertPracticeTest(test)
    }

    suspend fun deletePracticeTest(id: Long) = withContext(Dispatchers.IO) {
        dao.deletePracticeTest(id)
    }

    // --- Error Logs ---
    fun getAllErrorLogs(): Flow<List<ErrorLogEntity>> = dao.getAllErrorLogs()

    suspend fun saveErrorLog(log: ErrorLogEntity): Long = withContext(Dispatchers.IO) {
        if (log.id == 0L) {
            dao.insertErrorLog(log)
        } else {
            dao.updateErrorLog(log)
            log.id
        }
    }

    suspend fun deleteErrorLog(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteErrorLog(id)
    }

    // --- Weekly Assessments ---
    fun getAllWeeklyAssessments(): Flow<List<WeeklyAssessmentEntity>> = dao.getAllWeeklyAssessments()

    fun getWeeklyAssessment(weekNumber: Int): Flow<WeeklyAssessmentEntity?> =
        dao.getWeeklyAssessment(weekNumber)

    suspend fun saveWeeklyAssessment(assessment: WeeklyAssessmentEntity) = withContext(Dispatchers.IO) {
        dao.insertOrUpdateAssessment(assessment)
    }

    // --- Backup & Data Export / Import ---
    suspend fun exportBackupJson(): String = withContext(Dispatchers.IO) {
        val root = JSONObject()
        root.put("version", 1)
        root.put("exportTime", System.currentTimeMillis())

        val prefs = dao.getUserPreferencesSync()
        prefs?.let {
            val pObj = JSONObject()
            pObj.put("language", it.language)
            pObj.put("themeMode", it.themeMode)
            pObj.put("ieltsType", it.ieltsType)
            pObj.put("targetBandOverall", it.targetBandOverall)
            pObj.put("studyStartDate", it.studyStartDate)
            pObj.put("dailyGoalMinutes", it.dailyGoalMinutes)
            root.put("preferences", pObj)
        }

        // Export tasks progress
        val progressList = dao.getAllTaskProgressSync()
        val pArray = JSONArray()
        progressList.forEach { p ->
            val o = JSONObject()
            o.put("taskId", p.taskId)
            o.put("isCompleted", p.isCompleted)
            o.put("completedAt", p.completedAt ?: 0L)
            pArray.put(o)
        }
        root.put("taskProgress", pArray)

        // Export Journal entries
        val journals = dao.getAllJournalEntriesSync()
        val jArray = JSONArray()
        journals.forEach { j ->
            val o = JSONObject()
            o.put("id", j.id)
            o.put("date", j.date)
            o.put("title", j.title)
            o.put("content", j.content)
            o.put("section", j.section)
            o.put("whatLearned", j.whatLearned)
            o.put("mistakesMade", j.mistakesMade)
            o.put("improvementPlan", j.improvementPlan)
            o.put("nextAction", j.nextAction)
            o.put("tags", j.tags)
            o.put("timestamp", j.timestamp)
            jArray.put(o)
        }
        root.put("journalEntries", jArray)

        // Export Focus Timer Records
        val focusList = dao.getAllFocusRecordsSync()
        val fArray = JSONArray()
        focusList.forEach { f ->
            val o = JSONObject()
            o.put("id", f.id)
            o.put("date", f.date)
            o.put("timestamp", f.timestamp)
            o.put("mode", f.mode)
            o.put("plannedMinutes", f.plannedMinutes)
            o.put("actualMinutes", f.actualMinutes)
            o.put("section", f.section)
            o.put("linkedTaskId", f.linkedTaskId)
            o.put("notes", f.notes)
            o.put("isCompleted", f.isCompleted)
            fArray.put(o)
        }
        root.put("focusRecords", fArray)

        // Export Study Sessions
        val sessionsList = dao.getAllStudySessionsSync()
        val sArray = JSONArray()
        sessionsList.forEach { s ->
            val o = JSONObject()
            o.put("id", s.id)
            o.put("date", s.date)
            o.put("startTimestamp", s.startTimestamp)
            o.put("plannedDurationMinutes", s.plannedDurationMinutes)
            o.put("actualDurationMinutes", s.actualDurationMinutes)
            o.put("section", s.section)
            o.put("linkedTaskId", s.linkedTaskId)
            o.put("notes", s.notes)
            o.put("isCompleted", s.isCompleted)
            sArray.put(o)
        }
        root.put("studySessions", sArray)

        // Export Vocabulary
        val vocabList = dao.getAllVocabularySync()
        val vArray = JSONArray()
        vocabList.forEach { v ->
            val o = JSONObject()
            o.put("id", v.id)
            o.put("originalWord", v.originalWord)
            o.put("synonyms", v.synonyms)
            o.put("meaning", v.meaning)
            o.put("exampleSentence", v.exampleSentence)
            o.put("topicTag", v.topicTag)
            o.put("masteryStatus", v.masteryStatus)
            o.put("isReference", v.isReference)
            o.put("createdAt", v.createdAt)
            vArray.put(o)
        }
        root.put("vocabulary", vArray)

        // Export Practice Tests
        val testsList = dao.getAllPracticeTestsSync()
        val tArray = JSONArray()
        testsList.forEach { t ->
            val o = JSONObject()
            o.put("id", t.id)
            o.put("testDate", t.testDate)
            o.put("section", t.section)
            o.put("sourceBook", t.sourceBook)
            o.put("testNumber", t.testNumber)
            o.put("correctAnswers", t.correctAnswers)
            o.put("totalQuestions", t.totalQuestions)
            o.put("estimatedBand", t.estimatedBand)
            o.put("timeTakenMinutes", t.timeTakenMinutes)
            o.put("isTimed", t.isTimed)
            o.put("criteriaJson", t.criteriaJson)
            o.put("notes", t.notes)
            o.put("isHistoricalReference", t.isHistoricalReference)
            o.put("createdAt", t.createdAt)
            tArray.put(o)
        }
        root.put("practiceTests", tArray)

        // Export Error Logs
        val errList = dao.getAllErrorLogsSync()
        val eArray = JSONArray()
        errList.forEach { e ->
            val o = JSONObject()
            o.put("id", e.id)
            o.put("date", e.date)
            o.put("section", e.section)
            o.put("testReference", e.testReference)
            o.put("questionNumber", e.questionNumber)
            o.put("questionType", e.questionType)
            o.put("originalAnswer", e.originalAnswer)
            o.put("correctAnswer", e.correctAnswer)
            o.put("reasonForError", e.reasonForError)
            o.put("mistakeCategory", e.mistakeCategory)
            o.put("strategyToAvoid", e.strategyToAvoid)
            o.put("reviewStatus", e.reviewStatus)
            o.put("isHistoricalReference", e.isHistoricalReference)
            o.put("createdAt", e.createdAt)
            eArray.put(o)
        }
        root.put("errorLogs", eArray)

        // Export Weekly Assessments
        val assessList = dao.getAllWeeklyAssessmentsSync()
        val aArray = JSONArray()
        assessList.forEach { a ->
            val o = JSONObject()
            o.put("weekNumber", a.weekNumber)
            o.put("isCompleted", a.isCompleted)
            o.put("answersJson", a.answersJson)
            o.put("reflections", a.reflections)
            o.put("actionPlan", a.actionPlan)
            if (a.completedAt != null) {
                o.put("completedAt", a.completedAt)
            }
            aArray.put(o)
        }
        root.put("weeklyAssessments", aArray)

        root.toString(2)
    }

    suspend fun restoreBackupJson(jsonString: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val root = JSONObject(jsonString)
            if (!root.has("version")) return@withContext false

            if (root.has("preferences")) {
                val pObj = root.getJSONObject("preferences")
                val current = dao.getUserPreferencesSync() ?: UserPreferencesEntity()
                val updated = current.copy(
                    language = pObj.optString("language", current.language),
                    themeMode = pObj.optString("themeMode", current.themeMode),
                    ieltsType = pObj.optString("ieltsType", current.ieltsType),
                    targetBandOverall = pObj.optDouble("targetBandOverall", current.targetBandOverall),
                    studyStartDate = pObj.optString("studyStartDate", current.studyStartDate),
                    dailyGoalMinutes = pObj.optInt("dailyGoalMinutes", current.dailyGoalMinutes)
                )
                dao.insertUserPreferences(updated)
            }

            if (root.has("taskProgress")) {
                val pArray = root.getJSONArray("taskProgress")
                dao.resetAllTaskProgress()
                for (i in 0 until pArray.length()) {
                    val o = pArray.getJSONObject(i)
                    val taskId = o.getString("taskId")
                    val isCompleted = o.getBoolean("isCompleted")
                    val completedAt = o.optLong("completedAt", System.currentTimeMillis())
                    dao.setTaskProgress(TaskProgressEntity(taskId, isCompleted, completedAt))
                }
            }

            if (root.has("journalEntries")) {
                val jArray = root.getJSONArray("journalEntries")
                val entries = mutableListOf<JournalEntryEntity>()
                for (i in 0 until jArray.length()) {
                    val o = jArray.getJSONObject(i)
                    entries.add(
                        JournalEntryEntity(
                            id = o.optLong("id", 0L),
                            date = o.getString("date"),
                            title = o.getString("title"),
                            content = o.getString("content"),
                            section = o.getString("section"),
                            whatLearned = o.optString("whatLearned", ""),
                            mistakesMade = o.optString("mistakesMade", ""),
                            improvementPlan = o.optString("improvementPlan", ""),
                            nextAction = o.optString("nextAction", ""),
                            tags = o.optString("tags", ""),
                            timestamp = o.optLong("timestamp", System.currentTimeMillis())
                        )
                    )
                }
                if (entries.isNotEmpty()) {
                    dao.insertJournalEntries(entries)
                }
            }

            if (root.has("focusRecords")) {
                val fArray = root.getJSONArray("focusRecords")
                val records = mutableListOf<FocusTimerRecordEntity>()
                for (i in 0 until fArray.length()) {
                    val o = fArray.getJSONObject(i)
                    records.add(
                        FocusTimerRecordEntity(
                            id = o.optLong("id", 0L),
                            date = o.getString("date"),
                            timestamp = o.optLong("timestamp", System.currentTimeMillis()),
                            mode = o.getString("mode"),
                            plannedMinutes = o.getInt("plannedMinutes"),
                            actualMinutes = o.getInt("actualMinutes"),
                            section = o.getString("section"),
                            linkedTaskId = if (o.has("linkedTaskId") && !o.isNull("linkedTaskId")) o.getString("linkedTaskId") else null,
                            notes = o.optString("notes", ""),
                            isCompleted = o.optBoolean("isCompleted", true)
                        )
                    )
                }
                if (records.isNotEmpty()) {
                    dao.insertFocusRecords(records)
                }
            }

            if (root.has("studySessions")) {
                val sArray = root.getJSONArray("studySessions")
                val sessions = mutableListOf<StudySessionEntity>()
                for (i in 0 until sArray.length()) {
                    val o = sArray.getJSONObject(i)
                    sessions.add(
                        StudySessionEntity(
                            id = o.optLong("id", 0L),
                            date = o.getString("date"),
                            startTimestamp = o.optLong("startTimestamp", System.currentTimeMillis()),
                            plannedDurationMinutes = o.getInt("plannedDurationMinutes"),
                            actualDurationMinutes = o.getInt("actualDurationMinutes"),
                            section = o.getString("section"),
                            linkedTaskId = if (o.has("linkedTaskId") && !o.isNull("linkedTaskId")) o.getString("linkedTaskId") else null,
                            notes = o.optString("notes", ""),
                            isCompleted = o.optBoolean("isCompleted", true)
                        )
                    )
                }
                if (sessions.isNotEmpty()) {
                    dao.insertStudySessions(sessions)
                }
            }

            if (root.has("vocabulary")) {
                val vArray = root.getJSONArray("vocabulary")
                val list = mutableListOf<VocabularyEntryEntity>()
                for (i in 0 until vArray.length()) {
                    val o = vArray.getJSONObject(i)
                    list.add(
                        VocabularyEntryEntity(
                            id = o.optLong("id", 0L),
                            originalWord = o.getString("originalWord"),
                            synonyms = o.getString("synonyms"),
                            meaning = o.optString("meaning", ""),
                            exampleSentence = o.optString("exampleSentence", ""),
                            topicTag = o.optString("topicTag", "General"),
                            masteryStatus = o.optInt("masteryStatus", 0),
                            isReference = o.optBoolean("isReference", false),
                            createdAt = o.optLong("createdAt", System.currentTimeMillis())
                        )
                    )
                }
                if (list.isNotEmpty()) {
                    dao.insertVocabularyList(list)
                }
            }

            if (root.has("practiceTests")) {
                val tArray = root.getJSONArray("practiceTests")
                val tests = mutableListOf<PracticeTestEntity>()
                for (i in 0 until tArray.length()) {
                    val o = tArray.getJSONObject(i)
                    tests.add(
                        PracticeTestEntity(
                            id = o.optLong("id", 0L),
                            testDate = o.getString("testDate"),
                            section = o.getString("section"),
                            sourceBook = o.getString("sourceBook"),
                            testNumber = o.getString("testNumber"),
                            correctAnswers = o.optInt("correctAnswers", 0),
                            totalQuestions = o.optInt("totalQuestions", 40),
                            estimatedBand = o.optDouble("estimatedBand", 0.0),
                            timeTakenMinutes = o.optInt("timeTakenMinutes", 60),
                            isTimed = o.optBoolean("isTimed", true),
                            criteriaJson = o.optString("criteriaJson", ""),
                            notes = o.optString("notes", ""),
                            isHistoricalReference = o.optBoolean("isHistoricalReference", false),
                            createdAt = o.optLong("createdAt", System.currentTimeMillis())
                        )
                    )
                }
                if (tests.isNotEmpty()) {
                    dao.insertPracticeTests(tests)
                }
            }

            if (root.has("errorLogs")) {
                val eArray = root.getJSONArray("errorLogs")
                val logs = mutableListOf<ErrorLogEntity>()
                for (i in 0 until eArray.length()) {
                    val o = eArray.getJSONObject(i)
                    logs.add(
                        ErrorLogEntity(
                            id = o.optLong("id", 0L),
                            date = o.getString("date"),
                            section = o.getString("section"),
                            testReference = o.optString("testReference", ""),
                            questionNumber = o.optString("questionNumber", ""),
                            questionType = o.getString("questionType"),
                            originalAnswer = o.optString("originalAnswer", ""),
                            correctAnswer = o.optString("correctAnswer", ""),
                            reasonForError = o.optString("reasonForError", ""),
                            mistakeCategory = o.optString("mistakeCategory", ""),
                            strategyToAvoid = o.optString("strategyToAvoid", ""),
                            reviewStatus = o.optInt("reviewStatus", 0),
                            isHistoricalReference = o.optBoolean("isHistoricalReference", false),
                            createdAt = o.optLong("createdAt", System.currentTimeMillis())
                        )
                    )
                }
                if (logs.isNotEmpty()) {
                    dao.insertErrorLogs(logs)
                }
            }

            if (root.has("weeklyAssessments")) {
                val aArray = root.getJSONArray("weeklyAssessments")
                for (i in 0 until aArray.length()) {
                    val o = aArray.getJSONObject(i)
                    val weekNumber = o.getInt("weekNumber")
                    val isCompleted = o.optBoolean("isCompleted", false)
                    val answersJson = o.optString("answersJson", "{}")
                    val reflections = o.optString("reflections", "")
                    val actionPlan = o.optString("actionPlan", "")
                    val completedAt = if (o.has("completedAt") && !o.isNull("completedAt")) o.getLong("completedAt") else null

                    dao.insertOrUpdateAssessment(
                        WeeklyAssessmentEntity(
                            weekNumber = weekNumber,
                            isCompleted = isCompleted,
                            answersJson = answersJson,
                            reflections = reflections,
                            actionPlan = actionPlan,
                            completedAt = completedAt
                        )
                    )
                }
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun resetEntireApplication() = withContext(Dispatchers.IO) {
        dao.resetAllTaskProgress()
        dao.deleteAllJournalEntries()
        dao.deleteAllFocusRecords()
        dao.deletePreferences()
        AppDatabase.populateDatabase(dao)
    }
}
