package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.AppDao
import com.example.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserPreferencesEntity::class,
        StudyPhaseEntity::class,
        StudyDayEntity::class,
        StudyTaskEntity::class,
        TaskProgressEntity::class,
        JournalEntryEntity::class,
        StudySessionEntity::class,
        FocusTimerRecordEntity::class,
        VocabularyEntryEntity::class,
        PracticeTestEntity::class,
        ErrorLogEntity::class,
        WeeklyAssessmentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ielts_journey.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate initial plan in background
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    populateDatabase(database.appDao())
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(dao: AppDao) {
            dao.insertUserPreferences(InitialCurriculum.getDefaultPreferences())
            dao.insertPhases(InitialCurriculum.phases)
            dao.insertDays(InitialCurriculum.days)
            dao.insertTasks(InitialCurriculum.tasks)
            dao.insertVocabularyList(InitialCurriculum.vocabularyList)
            dao.insertPracticeTests(InitialCurriculum.referencePracticeTests)
            dao.insertErrorLogs(InitialCurriculum.referenceErrorLogs)
            dao.insertAssessments(InitialCurriculum.weeklyAssessments)
            // Notice: task_progress table is NOT populated.
            // Every task is initially unchecked.
        }
    }
}
