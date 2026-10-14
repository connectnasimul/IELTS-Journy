package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferencesEntity(
    @PrimaryKey val id: Int = 1,
    val language: String = "en", // "en" or "bn"
    val themeMode: String = "system", // "light", "dark", "system"
    val ieltsType: String = "Academic", // "Academic" or "General Training"
    val targetBandOverall: Double = 7.0,
    val targetBandListening: Double = 7.5,
    val targetBandReading: Double = 7.0,
    val targetBandWriting: Double = 6.5,
    val targetBandSpeaking: Double = 7.0,
    val studyStartDate: String = "", // ISO date yyyy-MM-dd
    val targetTestDate: String = "", // ISO date yyyy-MM-dd
    val dailyGoalMinutes: Int = 150, // 2.5 hours default
    val focusSessionMinutes: Int = 25,
    val shortBreakMinutes: Int = 5,
    val longBreakMinutes: Int = 15,
    val longBreakInterval: Int = 4,
    val remindersEnabled: Boolean = true,
    val isOnboarded: Boolean = true
)

@Entity(tableName = "study_phases")
data class StudyPhaseEntity(
    @PrimaryKey val id: Int, // 1 to 6
    val phaseNumber: Int,
    val title: String,
    val dayStart: Int,
    val dayEnd: Int,
    val goal: String,
    val description: String
)

@Entity(tableName = "study_days")
data class StudyDayEntity(
    @PrimaryKey val id: Int, // 1 to 41
    val dayNumber: Int,
    val phaseId: Int,
    val weekNumber: Int,
    val title: String,
    val learningObjective: String,
    val recommendedDurationMinutes: Int,
    val examinerTip: String = "",
    val commonMistake: String = "",
    val reflectionQuestions: String = "" // JSON or newline separated
)

@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey val id: String, // e.g. "d1_t1", "d1_t2"
    val dayId: Int,
    val parentTaskId: String? = null,
    val title: String,
    val description: String = "",
    val estimatedMinutes: Int = 30,
    val category: String = "General", // "Strategy", "Practice", "Analysis", "Review"
    val resourceUrl: String? = null,
    val isRequired: Boolean = true,
    val displayOrder: Int = 0
)

/**
 * Stores user completion states.
 * CRITICAL RULE: On fresh initialization, NO tasks are marked completed.
 * Completion states are completely separated from plan definitions.
 */
@Entity(tableName = "task_progress")
data class TaskProgressEntity(
    @PrimaryKey val taskId: String,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // yyyy-MM-dd
    val dayId: Int? = null,
    val section: String = "General", // Listening, Reading, Writing Task 1, Writing Task 2, Speaking, Vocabulary, Grammar, Mock Test
    val title: String,
    val content: String,
    val durationMinutes: Int = 0,
    val questionsAttempted: Int = 0,
    val questionsCorrect: Int = 0,
    val whatLearned: String = "",
    val mistakesMade: String = "",
    val improvementPlan: String = "",
    val nextAction: String = "",
    val tags: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_sessions")
data class StudySessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // yyyy-MM-dd
    val startTimestamp: Long,
    val plannedDurationMinutes: Int,
    val actualDurationMinutes: Int,
    val section: String,
    val linkedTaskId: String? = null,
    val notes: String = "",
    val isCompleted: Boolean = true
)

@Entity(tableName = "focus_timer_records")
data class FocusTimerRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // yyyy-MM-dd
    val timestamp: Long = System.currentTimeMillis(),
    val mode: String, // "Pomodoro", "Short Break", "Long Break", "Custom"
    val plannedMinutes: Int,
    val actualMinutes: Int,
    val section: String,
    val linkedTaskId: String? = null,
    val notes: String = "",
    val isCompleted: Boolean = true
)

@Entity(tableName = "vocabulary_entries")
data class VocabularyEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val originalWord: String,
    val synonyms: String,
    val meaning: String = "",
    val exampleSentence: String = "",
    val topicTag: String = "General",
    val masteryStatus: Int = 0, // 0 = Learning, 1 = Familiar, 2 = Mastered
    val isReference: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "practice_tests")
data class PracticeTestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val testDate: String, // yyyy-MM-dd
    val section: String, // "Listening", "Reading", "Writing Task 1", "Writing Task 2", "Speaking"
    val sourceBook: String, // e.g. "Cambridge IELTS 17"
    val testNumber: String, // e.g. "Test 2"
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 40,
    val estimatedBand: Double = 0.0,
    val timeTakenMinutes: Int = 60,
    val isTimed: Boolean = true,
    val criteriaJson: String = "", // For writing/speaking breakdown
    val notes: String = "",
    val isHistoricalReference: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "error_logs")
data class ErrorLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // yyyy-MM-dd
    val section: String, // "Reading", "Listening", "Writing", "Speaking"
    val testReference: String,
    val questionNumber: String,
    val questionType: String,
    val originalAnswer: String,
    val correctAnswer: String,
    val reasonForError: String,
    val mistakeCategory: String, // "Word-Grabbing Without Context", "Distractor Trap", etc.
    val strategyToAvoid: String,
    val reviewStatus: Int = 0, // 0 = Pending, 1 = Understood, 2 = Mastered
    val isHistoricalReference: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "weekly_assessments")
data class WeeklyAssessmentEntity(
    @PrimaryKey val weekNumber: Int, // 1 to 6
    val answersJson: String = "{}",
    val reflections: String = "",
    val actionPlan: String = "",
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)
