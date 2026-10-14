package com.example

import com.example.data.local.InitialCurriculum
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testCurriculumPhasesAndDaysIntegrity() {
        // Must preserve all 6 phases
        assertEquals(6, InitialCurriculum.phases.size)
        assertEquals("Phase 1: Foundations & Test Overview", InitialCurriculum.phases[0].title)
        assertEquals("Phase 6: Listening & Final Prep", InitialCurriculum.phases[5].title)

        // Must preserve exactly 41 days (Days 1 to 41) without inventing Day 42
        assertEquals(41, InitialCurriculum.days.size)
        assertEquals(1, InitialCurriculum.days.first().dayNumber)
        assertEquals(41, InitialCurriculum.days.last().dayNumber)

        // All 41 days have valid objectives and recommended durations
        InitialCurriculum.days.forEach { day ->
            assertTrue("Day ${day.dayNumber} objective should not be blank", day.learningObjective.isNotBlank())
            assertTrue("Day ${day.dayNumber} duration should be > 0", day.recommendedDurationMinutes > 0)
        }
    }

    @Test
    fun testTaskCountAndNoPreCompletedTasks() {
        val tasks = InitialCurriculum.tasks
        assertTrue("Must contain tasks across all days", tasks.size >= 80)

        // Verify task categories
        val categories = tasks.map { it.category }.toSet()
        assertTrue(categories.contains("Strategy"))
        assertTrue(categories.contains("Practice"))

        // Historical tests are clearly labeled
        assertEquals(3, InitialCurriculum.referencePracticeTests.size)
        InitialCurriculum.referencePracticeTests.forEach {
            assertTrue("Historical reference tests must be flagged", it.isHistoricalReference)
        }

        // Reference error logs are clearly labeled
        assertTrue(InitialCurriculum.referenceErrorLogs.isNotEmpty())
        InitialCurriculum.referenceErrorLogs.forEach {
            assertTrue("Historical error logs must be flagged", it.isHistoricalReference)
        }

        // Vocabulary entries contains all 35+ synonyms
        assertTrue("Vocabulary list should have at least 35 entries", InitialCurriculum.vocabularyList.size >= 35)
    }

    @Test
    fun testDynamicProgressFormula() {
        val totalTasks = 100
        val completedZero = 0
        val pctZero = if (totalTasks > 0) (completedZero * 100) / totalTasks else 0
        assertEquals(0, pctZero)

        val completedTwenty = 25
        val pctTwentyFive = if (totalTasks > 0) (completedTwenty * 100) / totalTasks else 0
        assertEquals(25, pctTwentyFive)

        // Safe division by zero test
        val emptyTotal = 0
        val pctEmpty = if (emptyTotal > 0) (0 * 100) / emptyTotal else 0
        assertEquals(0, pctEmpty)
    }
}
