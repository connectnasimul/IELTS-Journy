package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.WeeklyAssessmentEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val assessments by viewModel.weeklyAssessments.collectAsState()
    var selectedWeek by remember { mutableIntStateOf(1) }

    val currentAssessment = assessments.find { it.weekNumber == selectedWeek }
        ?: WeeklyAssessmentEntity(weekNumber = selectedWeek)

    var reflectionText by remember(selectedWeek, currentAssessment) {
        mutableStateOf(currentAssessment.reflections)
    }
    var actionPlanText by remember(selectedWeek, currentAssessment) {
        mutableStateOf(currentAssessment.actionPlan)
    }
    var isCompleted by remember(selectedWeek, currentAssessment) {
        mutableStateOf(currentAssessment.isCompleted)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("assessments_screen_container")
    ) {
            // Week Tab selector
            ScrollableTabRow(
                selectedTabIndex = selectedWeek - 1,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                (1..6).forEach { week ->
                    Tab(
                        selected = selectedWeek == week,
                        onClick = { selectedWeek = week },
                        text = {
                            Text(
                                text = "Week $week",
                                fontWeight = if (selectedWeek == week) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    val weekTitle = when (selectedWeek) {
                        1 -> "Week 1: Overview of the IELTS Test"
                        2 -> "Week 2: Reading Mastery Assessment"
                        3 -> "Week 3: Writing Task 2 Assessment"
                        4 -> "Week 4: Speaking Skills Assessment"
                        5 -> "Week 5: Writing Task 1 Assessment"
                        else -> "Week 6: Listening & Final Test Readiness"
                    }
                    Text(
                        text = weekTitle,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }

                // Questions / Criteria specifics for the week
                item(key = "criteria_card", contentType = "criteria") {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Assessment Criteria & Key Checkpoints",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.colors.accent
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            when (selectedWeek) {
                                1 -> {
                                    BulletPoint("Speaking Test: 3 Parts (11–14 minutes total)")
                                    BulletPoint("Listening Test: 4 Sections (approx 30 mins + 10 mins transfer)")
                                    BulletPoint("Reading Test: 60 minutes, 3 passages, 40 questions")
                                    BulletPoint("Writing Task 1: 20 mins, 150+ words; Task 2: 40 mins, 250+ words")
                                    BulletPoint("Writing Criteria: Task Achievement/Response, Coherence & Cohesion, Lexical Resource, Grammar Range & Accuracy")
                                }
                                2 -> {
                                    BulletPoint("Reading speed assessment: 200–250 words per minute ideal")
                                    BulletPoint("Strategies mastered: True/False/Not Given, Matching Headings, Multiple Choice")
                                    BulletPoint("Transfer time: Practicing 60-minute strict deadline without pause")
                                    BulletPoint("Paraphrase & Synonyms: spotting equivalent terms in passages")
                                }
                                3 -> {
                                    BulletPoint("Essay Structure: Introduction (Paraphrase + Thesis), 2 Body Paragraphs, Conclusion")
                                    BulletPoint("Paragraph cohesion: 1 central topic per paragraph with PEEL support")
                                    BulletPoint("Band 7+ Vocabulary: topic collocations without forced obscure words")
                                    BulletPoint("Proofreading: 3–5 minutes reserved to catch minor grammar slips")
                                }
                                4 -> {
                                    BulletPoint("Fluency & Coherence: speak continuously without unnatural pauses")
                                    BulletPoint("Part 2 Long Turn: use 1-min prep to note keywords; speak for full 2 minutes")
                                    BulletPoint("Part 3 Discussion: apply PREP (Point, Reason, Example, Point)")
                                    BulletPoint("Pronunciation: clear word stress, sentence intonation, and rhythm")
                                }
                                5 -> {
                                    BulletPoint("Academic Overview: clearly summarizes main trends with NO specific numbers")
                                    BulletPoint("Data Selection: reporting key high/low features without listing every detail")
                                    BulletPoint("Trend language: sharp increases vs steady plateaus and fluctuations")
                                    BulletPoint("Time management: 5 mins planning + 12 mins writing + 3 mins review")
                                }
                                6 -> {
                                    BulletPoint("Section 1 Vigilance: 100% accuracy on names, dates, numbers")
                                    BulletPoint("Prediction: anticipating word categories and grammar before audio")
                                    BulletPoint("Distractor Traps: listening for corrections and qualifying words")
                                    BulletPoint("10-Minute Transfer: strict double-checking of plurals and spelling")
                                }
                            }
                        }
                    }
                }

                // Reflection Input
                item(key = "reflection_card", contentType = "reflection") {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Weekly Reflections",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Document current strengths, weaknesses noticed, and overall confidence level:",
                                style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = reflectionText,
                                onValueChange = { reflectionText = it },
                                placeholder = { Text("What went well this week? Which areas felt most difficult?") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )
                        }
                    }
                }

                // Action Plan Input
                item(key = "action_plan_card", contentType = "action_plan") {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = Strings.get("action_plan", lang),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Specific strategies and priority drills for the upcoming week:",
                                style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = actionPlanText,
                                onValueChange = { actionPlanText = it },
                                placeholder = { Text("1. Drill True/False/Not Given questions\n2. Practice 2-minute speaking recordings...", color = AppTheme.colors.textMuted) },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )
                        }
                    }
                }

                // Save & Complete Button
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isCompleted,
                                onCheckedChange = { isCompleted = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AppTheme.colors.success,
                                    checkmarkColor = Color.White,
                                    uncheckedColor = AppTheme.colors.border
                                )
                            )
                            Text(
                                text = "Mark Week $selectedWeek as Evaluated",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.colors.textPrimary
                                )
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.saveAssessment(
                                    currentAssessment.copy(
                                        weekNumber = selectedWeek,
                                        reflections = reflectionText,
                                        actionPlan = actionPlanText,
                                        isCompleted = isCompleted,
                                        completedAt = if (isCompleted) System.currentTimeMillis() else null
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.accent,
                                contentColor = AppTheme.colors.accentOn
                            )
                        ) {
                            Text(Strings.get("save", lang))
                        }
                    }
                }
            }
        }
    }

@Composable
fun BulletPoint(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text("• ", fontWeight = FontWeight.Bold, color = AppTheme.colors.accent)
        Text(text = text, style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.textPrimary))
    }
}
