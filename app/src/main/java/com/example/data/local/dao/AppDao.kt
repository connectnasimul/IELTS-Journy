package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // --- Preferences ---
    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    fun getUserPreferences(): Flow<UserPreferencesEntity?>

    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    suspend fun getUserPreferencesSync(): UserPreferencesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPreferences(preferences: UserPreferencesEntity)

    // --- Study Phases ---
    @Query("SELECT * FROM study_phases ORDER BY phaseNumber ASC")
    fun getAllPhases(): Flow<List<StudyPhaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhases(phases: List<StudyPhaseEntity>)

    // --- Study Days ---
    @Query("SELECT * FROM study_days ORDER BY dayNumber ASC")
    fun getAllDays(): Flow<List<StudyDayEntity>>

    @Query("SELECT * FROM study_days WHERE phaseId = :phaseId ORDER BY dayNumber ASC")
    fun getDaysByPhase(phaseId: Int): Flow<List<StudyDayEntity>>

    @Query("SELECT * FROM study_days WHERE id = :dayId LIMIT 1")
    fun getDayById(dayId: Int): Flow<StudyDayEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<StudyDayEntity>)

    // --- Study Tasks ---
    @Query("SELECT * FROM study_tasks ORDER BY dayId ASC, displayOrder ASC")
    fun getAllTasks(): Flow<List<StudyTaskEntity>>

    @Query("SELECT * FROM study_tasks")
    suspend fun getAllTasksSync(): List<StudyTaskEntity>

    @Query("SELECT * FROM study_tasks WHERE dayId = :dayId ORDER BY displayOrder ASC")
    fun getTasksForDay(dayId: Int): Flow<List<StudyTaskEntity>>

    @Query("SELECT * FROM study_tasks WHERE dayId = :dayId")
    suspend fun getTasksForDaySync(dayId: Int): List<StudyTaskEntity>

    @Query("SELECT * FROM study_tasks WHERE dayId IN (SELECT id FROM study_days WHERE phaseId = :phaseId)")
    suspend fun getTasksForPhaseSync(phaseId: Int): List<StudyTaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<StudyTaskEntity>)

    // --- Task Progress (Separated & dynamic) ---
    @Query("SELECT * FROM task_progress")
    fun getAllTaskProgress(): Flow<List<TaskProgressEntity>>

    @Query("SELECT * FROM task_progress")
    suspend fun getAllTaskProgressSync(): List<TaskProgressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setTaskProgress(progress: TaskProgressEntity)

    @Query("DELETE FROM task_progress WHERE taskId = :taskId")
    suspend fun deleteTaskProgress(taskId: String)

    @Query("DELETE FROM task_progress")
    suspend fun resetAllTaskProgress()

    @Query("DELETE FROM task_progress WHERE taskId IN (:taskIds)")
    suspend fun resetTaskProgressForTasks(taskIds: List<String>)

    // --- Journal ---
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllJournalEntries(): Flow<List<JournalEntryEntity>>

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    suspend fun getAllJournalEntriesSync(): List<JournalEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(entry: JournalEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntries(entries: List<JournalEntryEntity>)

    @Update
    suspend fun updateJournalEntry(entry: JournalEntryEntity)

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteJournalEntry(id: Long)

    @Query("DELETE FROM journal_entries")
    suspend fun deleteAllJournalEntries()

    // --- Study Sessions ---
    @Query("SELECT * FROM study_sessions ORDER BY startTimestamp DESC")
    fun getAllStudySessions(): Flow<List<StudySessionEntity>>

    @Query("SELECT * FROM study_sessions ORDER BY startTimestamp DESC")
    suspend fun getAllStudySessionsSync(): List<StudySessionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudySession(session: StudySessionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudySessions(sessions: List<StudySessionEntity>)

    @Query("DELETE FROM study_sessions WHERE id = :id")
    suspend fun deleteStudySession(id: Long)

    // --- Focus Timer Records ---
    @Query("SELECT * FROM focus_timer_records ORDER BY timestamp DESC")
    fun getAllFocusRecords(): Flow<List<FocusTimerRecordEntity>>

    @Query("SELECT * FROM focus_timer_records ORDER BY timestamp DESC")
    suspend fun getAllFocusRecordsSync(): List<FocusTimerRecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFocusRecord(record: FocusTimerRecordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFocusRecords(records: List<FocusTimerRecordEntity>)

    @Query("DELETE FROM focus_timer_records")
    suspend fun deleteAllFocusRecords()

    // --- Vocabulary ---
    @Query("SELECT * FROM vocabulary_entries ORDER BY originalWord ASC")
    fun getAllVocabulary(): Flow<List<VocabularyEntryEntity>>

    @Query("SELECT * FROM vocabulary_entries ORDER BY id ASC")
    suspend fun getAllVocabularySync(): List<VocabularyEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(item: VocabularyEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabularyList(items: List<VocabularyEntryEntity>)

    @Update
    suspend fun updateVocabulary(item: VocabularyEntryEntity)

    @Query("DELETE FROM vocabulary_entries WHERE id = :id")
    suspend fun deleteVocabulary(id: Long)

    // --- Practice Tests ---
    @Query("SELECT * FROM practice_tests ORDER BY createdAt DESC")
    fun getAllPracticeTests(): Flow<List<PracticeTestEntity>>

    @Query("SELECT * FROM practice_tests ORDER BY createdAt DESC")
    suspend fun getAllPracticeTestsSync(): List<PracticeTestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPracticeTest(test: PracticeTestEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPracticeTests(tests: List<PracticeTestEntity>)

    @Query("DELETE FROM practice_tests WHERE id = :id")
    suspend fun deletePracticeTest(id: Long)

    // --- Error Logs ---
    @Query("SELECT * FROM error_logs ORDER BY createdAt DESC")
    fun getAllErrorLogs(): Flow<List<ErrorLogEntity>>

    @Query("SELECT * FROM error_logs ORDER BY createdAt DESC")
    suspend fun getAllErrorLogsSync(): List<ErrorLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErrorLog(log: ErrorLogEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErrorLogs(logs: List<ErrorLogEntity>)

    @Update
    suspend fun updateErrorLog(log: ErrorLogEntity)

    @Query("DELETE FROM error_logs WHERE id = :id")
    suspend fun deleteErrorLog(id: Long)

    // --- Weekly Assessments ---
    @Query("SELECT * FROM weekly_assessments ORDER BY weekNumber ASC")
    fun getAllWeeklyAssessments(): Flow<List<WeeklyAssessmentEntity>>

    @Query("SELECT * FROM weekly_assessments ORDER BY weekNumber ASC")
    suspend fun getAllWeeklyAssessmentsSync(): List<WeeklyAssessmentEntity>

    @Query("SELECT * FROM weekly_assessments WHERE weekNumber = :weekNumber LIMIT 1")
    fun getWeeklyAssessment(weekNumber: Int): Flow<WeeklyAssessmentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAssessment(assessment: WeeklyAssessmentEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAssessments(assessments: List<WeeklyAssessmentEntity>)

    // --- Full Reset ---
    @Query("DELETE FROM user_preferences")
    suspend fun deletePreferences()
}
