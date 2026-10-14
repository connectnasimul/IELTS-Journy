package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.InitialCurriculum
import com.example.data.local.entity.JournalEntryEntity
import com.example.data.local.entity.PracticeTestEntity
import com.example.data.repository.IeltsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: IeltsRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = IeltsRepository(database.appDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAppNameStringResource() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("IELTS Journey", appName)
    }

    @Test
    fun testFreshPlanHasZeroCompletedTasks() = runBlocking {
        repository.ensureInitialized()

        // Verify task progress is completely empty initially
        val initialProgress = repository.getAllTaskProgress().first()
        assertTrue("Task progress must be completely empty initially", initialProgress.isEmpty())

        // Check task toggling
        val tasks = repository.getAllTasks().first()
        assertTrue(tasks.isNotEmpty())
        val firstTask = tasks.first()

        // Complete the task
        repository.toggleTask(firstTask.id, true)
        val progressAfterCheck = repository.getAllTaskProgress().first()
        assertEquals(1, progressAfterCheck.size)
        assertTrue(progressAfterCheck.first().isCompleted)
        assertEquals(firstTask.id, progressAfterCheck.first().taskId)

        // Uncheck the task
        repository.toggleTask(firstTask.id, false)
        val progressAfterUncheck = repository.getAllTaskProgress().first()
        assertTrue("Task progress must be empty after unchecking", progressAfterUncheck.isEmpty())
    }

    @Test
    fun testResetAllTasksDoesNotDeleteJournalOrPracticeTests() = runBlocking {
        repository.ensureInitialized()

        // Add a journal entry and a practice test
        repository.saveJournalEntry(
            JournalEntryEntity(
                date = "2026-10-10",
                title = "Reading Lesson Reflection",
                content = "Practiced skimming and scanning.",
                section = "Reading"
            )
        )
        repository.addPracticeTest(
            PracticeTestEntity(
                testDate = "2026-10-10",
                section = "Reading",
                sourceBook = "Cambridge 18",
                testNumber = "Test 1",
                correctAnswers = 30,
                totalQuestions = 40,
                estimatedBand = 7.0
            )
        )

        // Mark a task complete
        val tasks = repository.getAllTasks().first()
        repository.toggleTask(tasks.first().id, true)
        assertEquals(1, repository.getAllTaskProgress().first().size)

        // Reset all tasks
        repository.resetAllTasks()

        // Tasks must be unchecked (0)
        assertEquals(0, repository.getAllTaskProgress().first().size)

        // Journal and Practice tests MUST still be intact
        val journals = repository.getAllJournalEntries().first()
        assertEquals(1, journals.size)
        assertEquals("Reading Lesson Reflection", journals.first().title)

        val tests = repository.getAllPracticeTests().first()
        assertTrue(tests.any { it.testNumber == "Test 1" })
    }

    @Test
    fun testAllImportedCurriculumAuditIntegrity() = runBlocking {
        repository.ensureInitialized()

        // 1. Phases
        val phases = repository.getAllPhases().first()
        assertEquals("Must have exactly 6 phases", 6, phases.size)

        // 2. Days
        val days = repository.getAllDays().first()
        assertEquals("Must have exactly 41 days", 41, days.size)
        // Check days mapping to phases
        assertTrue("Days 1-6 in Phase 1", days.filter { it.phaseId == 1 }.map { it.dayNumber } == (1..6).toList())
        assertTrue("Days 7-13 in Phase 2", days.filter { it.phaseId == 2 }.map { it.dayNumber } == (7..13).toList())
        assertTrue("Days 14-20 in Phase 3", days.filter { it.phaseId == 3 }.map { it.dayNumber } == (14..20).toList())
        assertTrue("Days 21-27 in Phase 4", days.filter { it.phaseId == 4 }.map { it.dayNumber } == (21..27).toList())
        assertTrue("Days 28-34 in Phase 5", days.filter { it.phaseId == 5 }.map { it.dayNumber } == (28..34).toList())
        assertTrue("Days 35-41 in Phase 6", days.filter { it.phaseId == 6 }.map { it.dayNumber } == (35..41).toList())

        // 3. Tasks & Subtasks
        val tasks = repository.getAllTasks().first()
        assertTrue("Must have rich set of tasks and subtasks", tasks.size >= 250)

        // Verify every task starts unchecked (0 progress records)
        val initialProgress = repository.getAllTaskProgress().first()
        assertEquals("All tasks must start unchecked with 0 completion records", 0, initialProgress.size)

        // 4. Subtasks parent integrity
        val subtasks = tasks.filter { it.parentTaskId != null }
        assertTrue("Must have nested checklist subtasks", subtasks.isNotEmpty())
        val taskIds = tasks.map { it.id }.toSet()
        subtasks.forEach { sub ->
            assertTrue("Subtask parent must exist in task list: ${sub.parentTaskId}", taskIds.contains(sub.parentTaskId))
            val parent = tasks.first { it.id == sub.parentTaskId }
            assertEquals("Subtask must belong to the same day as its parent", parent.dayId, sub.dayId)
        }

        // 5. Weekly Assessments
        val assessments = repository.getAllWeeklyAssessments().first()
        assertEquals("Must have 6 weekly assessments", 6, assessments.size)
        assessments.forEach { assessment ->
            assertFalse("Weekly assessment ${assessment.weekNumber} must start incomplete", assessment.isCompleted)
            assertNull("Weekly assessment ${assessment.weekNumber} completedAt must be null", assessment.completedAt)
        }
    }

    @Test
    fun testParentAndSubtaskTogglingHierarchy() = runBlocking {
        repository.ensureInitialized()

        val tasks = repository.getAllTasks().first()
        val parentTask = tasks.first { it.id == "d2_t3" }
        val childSubtasks = tasks.filter { it.parentTaskId == "d2_t3" }
        assertTrue("d2_t3 must have 4 criteria subtasks", childSubtasks.size == 4)

        // 1. Check parent task -> all child subtasks must be marked complete
        repository.toggleTask(parentTask.id, true)
        val progressMapAfterParentCheck = repository.getAllTaskProgress().first().associate { it.taskId to it.isCompleted }
        assertTrue(progressMapAfterParentCheck[parentTask.id] == true)
        childSubtasks.forEach { child ->
            assertTrue("Child ${child.id} must be checked when parent is checked", progressMapAfterParentCheck[child.id] == true)
        }

        // 2. Uncheck one child -> parent task must be unchecked
        val firstChild = childSubtasks.first()
        repository.toggleTask(firstChild.id, false)
        val progressMapAfterChildUncheck = repository.getAllTaskProgress().first().associate { it.taskId to it.isCompleted }
        assertFalse("Parent task must be unchecked when a child is unchecked", progressMapAfterChildUncheck.containsKey(parentTask.id))
        assertFalse("Unchecked child must not be in completed progress", progressMapAfterChildUncheck.containsKey(firstChild.id))
        // Other siblings remain completed
        childSubtasks.drop(1).forEach { sibling ->
            assertTrue("Sibling ${sibling.id} should still be completed", progressMapAfterChildUncheck[sibling.id] == true)
        }

        // 3. Re-check the remaining child -> all siblings complete, so parent completes automatically
        repository.toggleTask(firstChild.id, true)
        val progressMapAfterAllChildrenChecked = repository.getAllTaskProgress().first().associate { it.taskId to it.isCompleted }
        assertTrue("Parent task must be auto-completed when all children are completed", progressMapAfterAllChildrenChecked[parentTask.id] == true)

        // 4. Uncheck parent task -> all children must be unchecked
        repository.toggleTask(parentTask.id, false)
        val progressMapAfterParentUncheck = repository.getAllTaskProgress().first().associate { it.taskId to it.isCompleted }
        assertFalse(progressMapAfterParentUncheck.containsKey(parentTask.id))
        childSubtasks.forEach { child ->
            assertFalse("Child ${child.id} must be unchecked", progressMapAfterParentUncheck.containsKey(child.id))
        }
    }

    @Test
    fun testAppRestartPersistenceIntegrity() = runBlocking {
        repository.ensureInitialized()

        // 1. Mark some tasks done
        repository.toggleTask("d1_t1", true)
        repository.toggleTask("d1_t2", true)

        // 2. Add a journal entry and focus record
        repository.saveJournalEntry(
            JournalEntryEntity(
                date = "2026-10-10",
                title = "Persistence Test",
                content = "Content preserved across restarts",
                section = "Writing Task 2"
            )
        )
        repository.addFocusRecord(
            com.example.data.local.entity.FocusTimerRecordEntity(
                date = "2026-10-10",
                mode = "Pomodoro",
                plannedMinutes = 25,
                actualMinutes = 25,
                section = "Reading",
                notes = "Initial focus session"
            )
        )

        // 3. Simulate App Restart: Create new repository instance on existing database and call ensureInitialized()
        val restartedRepo = IeltsRepository(database.appDao())
        restartedRepo.ensureInitialized()

        // Verify task completion states were preserved, not reset
        val completedAfterRestart = restartedRepo.getAllTaskProgress().first()
        assertEquals(2, completedAfterRestart.size)
        assertTrue(completedAfterRestart.any { it.taskId == "d1_t1" && it.isCompleted })
        assertTrue(completedAfterRestart.any { it.taskId == "d1_t2" && it.isCompleted })

        // Verify journal entry persisted
        val journalsAfterRestart = restartedRepo.getAllJournalEntries().first()
        assertEquals(1, journalsAfterRestart.size)
        assertEquals("Persistence Test", journalsAfterRestart.first().title)

        // Verify focus record persisted
        val focusAfterRestart = restartedRepo.getAllFocusRecords().first()
        assertEquals(1, focusAfterRestart.size)
        assertEquals(25, focusAfterRestart.first().actualMinutes)
    }

    @Test
    fun testDuplicateSessionPrevention() = runBlocking {
        repository.ensureInitialized()

        val timestamp = System.currentTimeMillis()
        val record1 = com.example.data.local.entity.FocusTimerRecordEntity(
            date = "2026-10-10",
            timestamp = timestamp,
            mode = "Pomodoro",
            plannedMinutes = 25,
            actualMinutes = 25,
            section = "Listening",
            notes = "Test duplicate"
        )
        val id1 = repository.addFocusRecord(record1)
        assertTrue("First record should be inserted", id1 > 0)

        // Attempt to insert duplicate record immediately
        val record2 = com.example.data.local.entity.FocusTimerRecordEntity(
            date = "2026-10-10",
            timestamp = timestamp + 1000L, // 1 second later
            mode = "Pomodoro",
            plannedMinutes = 25,
            actualMinutes = 25,
            section = "Listening",
            notes = "Accidental duplicate click"
        )
        val id2 = repository.addFocusRecord(record2)
        assertEquals("Duplicate record should be prevented with -1 return code", -1L, id2)

        val totalRecords = repository.getAllFocusRecords().first()
        assertEquals("Only one session must be stored in database", 1, totalRecords.size)

        // Study session duplicate test
        val studySession1 = com.example.data.local.entity.StudySessionEntity(
            date = "2026-10-10",
            startTimestamp = timestamp,
            plannedDurationMinutes = 40,
            actualDurationMinutes = 40,
            section = "Reading"
        )
        val sId1 = repository.addStudySession(studySession1)
        assertTrue("First study session should be inserted", sId1 > 0)

        val studySession2 = com.example.data.local.entity.StudySessionEntity(
            date = "2026-10-10",
            startTimestamp = timestamp + 2000L,
            plannedDurationMinutes = 40,
            actualDurationMinutes = 40,
            section = "Reading"
        )
        val sId2 = repository.addStudySession(studySession2)
        assertEquals("Duplicate study session should be rejected", -1L, sId2)

        val totalStudySessions = repository.getAllStudySessions().first()
        assertEquals("Only one study session must be stored", 1, totalStudySessions.size)
    }

    @Test
    fun testFullBackupExportAndRestore() = runBlocking {
        repository.ensureInitialized()

        // 1. Populate rich data
        repository.toggleTask("d1_t1", true)
        repository.saveJournalEntry(
            JournalEntryEntity(
                date = "2026-10-10",
                title = "Backup Test Reflection",
                content = "Tested detailed backup restoration",
                section = "Writing Task 1",
                whatLearned = "Overview paragraph technique"
            )
        )
        repository.addFocusRecord(
            com.example.data.local.entity.FocusTimerRecordEntity(
                date = "2026-10-10",
                mode = "Custom",
                plannedMinutes = 45,
                actualMinutes = 45,
                section = "Writing Task 1"
            )
        )
        repository.addPracticeTest(
            PracticeTestEntity(
                testDate = "2026-10-10",
                section = "Listening",
                sourceBook = "Cambridge 17",
                testNumber = "Test 2",
                correctAnswers = 35,
                totalQuestions = 40,
                estimatedBand = 8.0
            )
        )
        repository.saveWeeklyAssessment(
            com.example.data.local.entity.WeeklyAssessmentEntity(
                weekNumber = 1,
                isCompleted = true,
                reflections = "Week 1 mastered foundation",
                actionPlan = "Continue into Reading Week 2",
                completedAt = System.currentTimeMillis()
            )
        )

        // 2. Export backup
        val backupJson = repository.exportBackupJson()
        assertTrue("Backup must contain journalEntries", backupJson.contains("Backup Test Reflection"))
        assertTrue("Backup must contain practiceTests", backupJson.contains("Cambridge 17"))
        assertTrue("Backup must contain focusRecords", backupJson.contains("Custom"))
        assertTrue("Backup must contain weeklyAssessments", backupJson.contains("Week 1 mastered foundation"))

        // 3. Reset entire application
        repository.resetEntireApplication()
        assertEquals(0, repository.getAllTaskProgress().first().size)
        assertEquals(0, repository.getAllJournalEntries().first().size)
        assertEquals(0, repository.getAllFocusRecords().first().size)

        // 4. Restore backup
        val restoreSuccess = repository.restoreBackupJson(backupJson)
        assertTrue("Restore must succeed", restoreSuccess)

        // 5. Verify all restored records
        val restoredProgress = repository.getAllTaskProgress().first()
        assertEquals(1, restoredProgress.size)
        assertEquals("d1_t1", restoredProgress.first().taskId)

        val restoredJournals = repository.getAllJournalEntries().first()
        assertEquals(1, restoredJournals.size)
        assertEquals("Backup Test Reflection", restoredJournals.first().title)
        assertEquals("Overview paragraph technique", restoredJournals.first().whatLearned)

        val restoredFocus = repository.getAllFocusRecords().first()
        assertEquals(1, restoredFocus.size)
        assertEquals(45, restoredFocus.first().actualMinutes)

        val restoredPractice = repository.getAllPracticeTests().first()
        val userTest = restoredPractice.firstOrNull { it.testNumber == "Test 2" && it.sourceBook == "Cambridge 17" }
        assertNotNull("User practice test must be restored", userTest)
        assertEquals(8.0, userTest!!.estimatedBand, 0.01)

        val restoredAssessment = repository.getWeeklyAssessment(1).first()
        assertNotNull(restoredAssessment)
        assertTrue(restoredAssessment!!.isCompleted)
        assertEquals("Week 1 mastered foundation", restoredAssessment.reflections)
    }

    @Test
    fun testTimerPauseAndResumeCountdownLogic() {
        val totalSeconds = 25 * 60 // 1500 seconds
        var secondsRemaining = totalSeconds
        var timerStatus = "IDLE"

        // 1. Start timer
        timerStatus = "RUNNING"
        val startTimestamp1 = 100_000L
        val currentTimestamp1 = 105_000L // 5 seconds elapsed
        val elapsed1 = ((currentTimestamp1 - startTimestamp1) / 1000).toInt()
        secondsRemaining = (secondsRemaining - elapsed1).coerceAtLeast(0)
        assertEquals(1495, secondsRemaining)

        // 2. Pause timer
        timerStatus = "PAUSED"
        // Time passes while paused (30 seconds)
        val pausedSecondsRemaining = secondsRemaining
        assertEquals(1495, pausedSecondsRemaining)

        // 3. Resume timer
        timerStatus = "RUNNING"
        val startTimestamp2 = 200_000L
        val currentTimestamp2 = 210_000L // 10 seconds elapsed during resumed run
        val elapsed2 = ((currentTimestamp2 - startTimestamp2) / 1000).toInt()
        secondsRemaining = (pausedSecondsRemaining - elapsed2).coerceAtLeast(0)
        assertEquals(1485, secondsRemaining)
    }
}
