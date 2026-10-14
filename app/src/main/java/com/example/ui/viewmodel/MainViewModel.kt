package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entity.*
import com.example.data.repository.IeltsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

enum class NavTab {
    HOME, PLAN, FOCUS, JOURNAL, PROGRESS
}

enum class SecondaryScreen {
    NONE, VOCABULARY, PRACTICE_TESTS, ERROR_LOG, ASSESSMENTS, RESOURCES, SETTINGS, ONBOARDING
}

data class DayWithTasks(
    val day: StudyDayEntity,
    val tasks: List<StudyTaskEntity>,
    val completedTaskCount: Int,
    val totalTaskCount: Int,
    val progressPercent: Int
)

data class PhaseWithDays(
    val phase: StudyPhaseEntity,
    val days: List<DayWithTasks>,
    val completedTaskCount: Int,
    val totalTaskCount: Int,
    val progressPercent: Int
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IeltsRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = IeltsRepository(db.appDao())
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    // --- Navigation State ---
    private val _currentTab = MutableStateFlow(NavTab.HOME)
    val currentTab: StateFlow<NavTab> = _currentTab.asStateFlow()

    private val _secondaryScreen = MutableStateFlow(SecondaryScreen.NONE)
    val secondaryScreen: StateFlow<SecondaryScreen> = _secondaryScreen.asStateFlow()

    private val _selectedDayNumber = MutableStateFlow<Int?>(null)
    val selectedDayNumber: StateFlow<Int?> = _selectedDayNumber.asStateFlow()

    fun setTab(tab: NavTab) {
        _currentTab.value = tab
        _secondaryScreen.value = SecondaryScreen.NONE
    }

    fun openSecondaryScreen(screen: SecondaryScreen) {
        _secondaryScreen.value = screen
    }

    fun closeSecondaryScreen() {
        _secondaryScreen.value = SecondaryScreen.NONE
    }

    fun selectDay(dayNumber: Int?) {
        _selectedDayNumber.value = dayNumber
    }

    // --- Base Flows ---
    val userPreferences: StateFlow<UserPreferencesEntity> = repository.getUserPreferences()
        .filterNotNull()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserPreferencesEntity()
        )

    val phases: StateFlow<List<StudyPhaseEntity>> = repository.getAllPhases()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val days: StateFlow<List<StudyDayEntity>> = repository.getAllDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasks: StateFlow<List<StudyTaskEntity>> = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val taskProgressList: StateFlow<List<TaskProgressEntity>> = repository.getAllTaskProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedTaskIds: StateFlow<Set<String>> = taskProgressList
        .map { list -> list.filter { it.isCompleted }.map { it.taskId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val journalEntries: StateFlow<List<JournalEntryEntity>> = repository.getAllJournalEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val focusRecords: StateFlow<List<FocusTimerRecordEntity>> = repository.getAllFocusRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vocabularyList: StateFlow<List<VocabularyEntryEntity>> = repository.getAllVocabulary()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val practiceTests: StateFlow<List<PracticeTestEntity>> = repository.getAllPracticeTests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val errorLogs: StateFlow<List<ErrorLogEntity>> = repository.getAllErrorLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val weeklyAssessments: StateFlow<List<WeeklyAssessmentEntity>> = repository.getAllWeeklyAssessments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Active Study Day calculation ---
    val currentActiveDayNumber: StateFlow<Int> = combine(userPreferences, days) { prefs, allDays ->
        if (allDays.isEmpty()) return@combine 1
        val startDateStr = prefs.studyStartDate
        if (startDateStr.isBlank()) return@combine 1
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDate = sdf.parse(startDateStr) ?: return@combine 1
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            val diffMs = today.time - startDate.time
            val diffDays = TimeUnit.MILLISECONDS.toDays(diffMs).toInt()
            (diffDays + 1).coerceIn(1, 41)
        } catch (e: Exception) {
            1
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)

    // --- Structured Phase and Day hierarchy with accurate dynamic progress ---
    val phasesWithDays: StateFlow<List<PhaseWithDays>> = combine(
        phases,
        days,
        tasks,
        completedTaskIds
    ) { pList, dList, tList, completedIds ->
        val tasksByDay = tList.groupBy { it.dayId }

        pList.map { phase ->
            val phaseDays = dList.filter { it.phaseId == phase.id }
            val dayWithTasksList = phaseDays.map { day ->
                val dayTasks = tasksByDay[day.id] ?: emptyList()
                val completed = dayTasks.count { completedIds.contains(it.id) }
                val total = dayTasks.size
                val pct = if (total > 0) (completed * 100) / total else 0
                DayWithTasks(day, dayTasks, completed, total, pct)
            }
            val phaseCompleted = dayWithTasksList.sumOf { it.completedTaskCount }
            val phaseTotal = dayWithTasksList.sumOf { it.totalTaskCount }
            val phasePct = if (phaseTotal > 0) (phaseCompleted * 100) / phaseTotal else 0
            PhaseWithDays(phase, dayWithTasksList, phaseCompleted, phaseTotal, phasePct)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Overall Metrics ---
    val overallTaskProgressPercent: StateFlow<Int> = combine(tasks, completedTaskIds) { tList, cIds ->
        val total = tList.size
        val done = tList.count { cIds.contains(it.id) }
        if (total > 0) (done * 100) / total else 0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val completedDaysCount: StateFlow<Int> = phasesWithDays.map { pList ->
        pList.flatMap { it.days }.count { it.totalTaskCount > 0 && it.completedTaskCount == it.totalTaskCount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Total focus minutes
    val totalFocusMinutes: StateFlow<Int> = focusRecords.map { records ->
        records.filter { it.isCompleted }.sumOf { it.actualMinutes }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val todayFocusMinutes: StateFlow<Int> = focusRecords.map { records ->
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        records.filter { it.isCompleted && it.date == todayStr }.sumOf { it.actualMinutes }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val todaySessionsCount: StateFlow<Int> = focusRecords.map { records ->
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        records.count { it.isCompleted && it.date == todayStr }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Study Streak Calculation: consecutive calendar days with at least 1 study action
    val studyStreakDays: StateFlow<Int> = combine(
        taskProgressList,
        focusRecords,
        journalEntries
    ) { progress, focus, journal ->
        val activeDates = mutableSetOf<String>()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        progress.filter { it.isCompleted && it.completedAt != null }.forEach {
            activeDates.add(sdf.format(Date(it.completedAt!!)))
        }
        focus.filter { it.isCompleted }.forEach {
            activeDates.add(it.date)
        }
        journal.forEach {
            activeDates.add(it.date)
        }

        if (activeDates.isEmpty()) return@combine 0

        var streak = 0
        val cal = Calendar.getInstance()
        val todayStr = sdf.format(cal.time)

        // If studied today, count today; else if studied yesterday, count from yesterday
        if (!activeDates.contains(todayStr)) {
            cal.add(Calendar.DAY_OF_YEAR, -1)
            val yesterdayStr = sdf.format(cal.time)
            if (!activeDates.contains(yesterdayStr)) {
                return@combine 0
            }
        }

        while (true) {
            val dateStr = sdf.format(cal.time)
            if (activeDates.contains(dateStr)) {
                streak++
                cal.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }
        streak
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // --- Actions ---

    fun toggleTask(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleTask(taskId, isCompleted)
        }
    }

    fun resetAllTasks(onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.resetAllTasks()
            onDone()
        }
    }

    fun resetPhaseTasks(phaseId: Int, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.resetPhaseTasks(phaseId)
            onDone()
        }
    }

    fun resetDayTasks(dayId: Int, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.resetDayTasks(dayId)
            onDone()
        }
    }

    fun saveJournal(entry: JournalEntryEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.saveJournalEntry(entry)
            onDone()
        }
    }

    fun deleteJournal(id: Long) {
        viewModelScope.launch {
            repository.deleteJournalEntry(id)
        }
    }

    fun saveFocusRecord(record: FocusTimerRecordEntity) {
        viewModelScope.launch {
            repository.addFocusRecord(record)
        }
    }

    fun clearFocusHistory(onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.deleteAllFocusRecords()
            onDone()
        }
    }

    fun saveVocabulary(item: VocabularyEntryEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.saveVocabulary(item)
            onDone()
        }
    }

    fun deleteVocabulary(id: Long) {
        viewModelScope.launch {
            repository.deleteVocabulary(id)
        }
    }

    fun savePracticeTest(test: PracticeTestEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.addPracticeTest(test)
            onDone()
        }
    }

    fun deletePracticeTest(id: Long) {
        viewModelScope.launch {
            repository.deletePracticeTest(id)
        }
    }

    fun saveErrorLog(log: ErrorLogEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.saveErrorLog(log)
            onDone()
        }
    }

    fun deleteErrorLog(id: Long) {
        viewModelScope.launch {
            repository.deleteErrorLog(id)
        }
    }

    fun saveAssessment(assessment: WeeklyAssessmentEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.saveWeeklyAssessment(assessment)
            onDone()
        }
    }

    fun updatePreferences(prefs: UserPreferencesEntity) {
        viewModelScope.launch {
            repository.updateUserPreferences(prefs)
        }
    }

    fun setLanguage(lang: String) {
        val current = userPreferences.value
        updatePreferences(current.copy(language = lang))
    }

    fun setThemeMode(mode: String) {
        val current = userPreferences.value
        updatePreferences(current.copy(themeMode = mode))
    }

    fun setStudyStartDate(dateStr: String) {
        val current = userPreferences.value
        updatePreferences(current.copy(studyStartDate = dateStr))
    }

    fun exportBackup(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val json = repository.exportBackupJson()
            onResult(json)
        }
    }

    fun importBackup(json: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.restoreBackupJson(json)
            onResult(success)
        }
    }

    fun resetEntireApp(onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.resetEntireApplication()
            onDone()
        }
    }
}
