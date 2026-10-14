package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel

@Composable
fun ProgressScreen(
    viewModel: MainViewModel,
    lang: String
) {
    val overallPercent by viewModel.overallTaskProgressPercent.collectAsState()
    val completedDays by viewModel.completedDaysCount.collectAsState()
    val totalFocusMins by viewModel.totalFocusMinutes.collectAsState()
    val streakDays by viewModel.studyStreakDays.collectAsState()
    val phasesWithDays by viewModel.phasesWithDays.collectAsState()
    val practiceTests by viewModel.practiceTests.collectAsState()
    val errorLogs by viewModel.errorLogs.collectAsState()
    val prefs by viewModel.userPreferences.collectAsState()

    val totalTasks = remember(phasesWithDays) { phasesWithDays.sumOf { it.totalTaskCount } }
    val completedTasks = remember(phasesWithDays) { phasesWithDays.sumOf { it.completedTaskCount } }
    val topErrorCategories = remember(errorLogs) {
        errorLogs.groupBy { it.mistakeCategory }
            .mapValues { it.value.size }
            .entries
            .sortedByDescending { it.value }
            .take(5)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("progress_screen_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Overall Completion Hero Card
        item(key = "progress_hero_card", contentType = "hero") {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (AppTheme.colors.isDark) Color(0xFF1E3A8A) else NavyPrimary
                ),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = Strings.get("overall_progress", lang),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$overallPercent%",
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "$completedTasks of $totalTasks tasks",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFE2E8F0))
                            )
                            Text(
                                text = "$completedDays of 41 days done",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFCBD5E1))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { overallPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AppTheme.colors.progressIndicator,
                        trackColor = Color.White.copy(alpha = 0.25f)
                    )
                }
            }
        }

        // 2. Study Time & Streak Metrics
        item(key = "progress_metrics_row", contentType = "metrics") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    title = "Total Focus Time",
                    value = "${totalFocusMins / 60}h ${totalFocusMins % 60}m",
                    subtitle = "From focus timer",
                    icon = Icons.Default.Timer,
                    iconColor = AppTheme.colors.accent,
                    modifier = Modifier.weight(1f)
                )

                MetricCard(
                    title = "Study Streak",
                    value = "$streakDays Days",
                    subtitle = "Consecutive active days",
                    icon = Icons.Default.LocalFireDepartment,
                    iconColor = AppTheme.colors.warning,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 3. Phase Breakdown
        item(key = "progress_phase_breakdown", contentType = "breakdown") {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = Strings.get("phase_breakdown", lang),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.colors.textPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    phasesWithDays.forEach { p ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = p.phase.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = AppTheme.colors.textPrimary
                                    ),
                                    maxLines = 1
                                )
                                Text(
                                    text = "${p.progressPercent}% (${p.completedTaskCount}/${p.totalTaskCount})",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = AppTheme.colors.accent,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { p.progressPercent / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = AppTheme.colors.progressIndicator,
                                trackColor = AppTheme.colors.progressTrack
                            )
                        }
                    }
                }
            }
        }

        // 4. Target vs Current Band Aspirations
        item(key = "progress_target_band", contentType = "target_band") {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Target Band Scores",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.colors.textPrimary
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AppTheme.colors.warningContainer
                        ) {
                            Text(
                                text = "Overall ${prefs.targetBandOverall}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = AppTheme.colors.onWarningContainer,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ScoreTargetPill("Listening", prefs.targetBandListening)
                        ScoreTargetPill("Reading", prefs.targetBandReading)
                        ScoreTargetPill("Writing", prefs.targetBandWriting)
                        ScoreTargetPill("Speaking", prefs.targetBandSpeaking)
                    }
                }
            }
        }

        // 5. Practice Tests History & Accuracy
        item(key = "progress_practice_tests", contentType = "test_history") {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = Strings.get("practice_tests_history", lang),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (practiceTests.isEmpty()) {
                        Text(
                            text = "No practice tests recorded yet.",
                            style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textMuted)
                        )
                    } else {
                        practiceTests.take(4).forEach { test ->
                            val accuracy = if (test.totalQuestions > 0) (test.correctAnswers * 100) / test.totalQuestions else 0
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${test.section}: ${test.testNumber}",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                        )
                                        if (test.isHistoricalReference) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = AppTheme.colors.surfaceVariant
                                            ) {
                                                Text(
                                                    text = "REF",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = 9.sp,
                                                        color = AppTheme.colors.textSecondary,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = "${test.sourceBook} • ${test.testDate}",
                                        style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "${test.correctAnswers}/${test.totalQuestions} ($accuracy%)",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    if (test.estimatedBand > 0) {
                                        Text(
                                            text = "Band ~${test.estimatedBand}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = AppTheme.colors.accent,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                            HorizontalDivider(color = AppTheme.colors.borderSubtle)
                        }
                    }
                }
            }
        }

        // 6. Most Frequent Error Categories
        item(key = "progress_error_categories", contentType = "error_cats") {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = Strings.get("error_categories", lang),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (topErrorCategories.isEmpty()) {
                        Text(
                            text = "No errors logged yet. Error analysis will populate here.",
                            style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textMuted)
                        )
                    } else {
                        topErrorCategories.forEach { (category, count) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = AppTheme.colors.textPrimary
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = AppTheme.colors.errorContainer
                                ) {
                                    Text(
                                        text = "$count errors",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = AppTheme.colors.onErrorContainer,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Disclaimer Note
        item {
            Text(
                text = "Note: Task completion reflects study consistency. Actual IELTS band scores depend on authentic exam performance evaluated against official IELTS criteria.",
                style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textMuted),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = AppTheme.colors.textSecondary,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.textPrimary
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = AppTheme.colors.textMuted,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
fun ScoreTargetPill(skill: String, target: Double) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = AppTheme.colors.surfaceVariant,
        modifier = Modifier.padding(2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = skill,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = AppTheme.colors.textSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = "$target",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.accent
                )
            )
        }
    }
}
