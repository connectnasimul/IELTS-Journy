package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.StudyTaskEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SecondaryScreen

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    lang: String,
    onNavigateToTab: (NavTab) -> Unit,
    onOpenSecondary: (SecondaryScreen) -> Unit,
    onSelectDay: (Int) -> Unit
) {
    val activeDayNum by viewModel.currentActiveDayNumber.collectAsState()
    val overallPercent by viewModel.overallTaskProgressPercent.collectAsState()
    val completedDays by viewModel.completedDaysCount.collectAsState()
    val streakDays by viewModel.studyStreakDays.collectAsState()
    val todayFocusMins by viewModel.todayFocusMinutes.collectAsState()
    val todaySessions by viewModel.todaySessionsCount.collectAsState()
    val phasesWithDays by viewModel.phasesWithDays.collectAsState()
    val completedTaskIds by viewModel.completedTaskIds.collectAsState()
    val prefs by viewModel.userPreferences.collectAsState()

    // Find active day data
    val activeDayWithTasks = remember(phasesWithDays, activeDayNum) {
        phasesWithDays.flatMap { it.days }.find { it.day.dayNumber == activeDayNum }
    }
    val rootTasks = remember(activeDayWithTasks?.tasks) {
        activeDayWithTasks?.tasks?.filter { it.parentTaskId == null } ?: emptyList()
    }
    val subtasksByParent = remember(activeDayWithTasks?.tasks) {
        activeDayWithTasks?.tasks?.filter { it.parentTaskId != null }?.groupBy { it.parentTaskId } ?: emptyMap()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Welcome & Study Streak Banner
        item(key = "streak_banner_card") {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (AppTheme.colors.isDark) Color(0xFF1E3A8A) else NavyPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("streak_banner_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White.copy(alpha = 0.18f)
                        ) {
                            Text(
                                text = prefs.ieltsType,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.5.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        // Compact Streak Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (streakDays > 0) Color(0xFFF59E0B).copy(alpha = 0.25f) else Color.White.copy(alpha = 0.15f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = if (streakDays > 0) Color(0xFFFBBF24) else Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$streakDays ${if (lang == "bn") "দিন" else "Days"}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = Strings.get("tagline_short", lang),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = Strings.get("app_tagline", lang),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFE2E8F0)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // 2. Overall Progress Summary Card
        item(key = "overall_progress_card", contentType = "overall_progress") {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("overall_progress_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Strings.get("overall_progress", lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.colors.textPrimary
                            )
                        )
                        Text(
                            text = "$overallPercent%",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = AppTheme.colors.accent
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { overallPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = AppTheme.colors.progressIndicator,
                        trackColor = AppTheme.colors.progressTrack
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(
                            label = Strings.get("days_active", lang),
                            value = "$completedDays / 41",
                            icon = Icons.Default.CalendarToday
                        )
                        StatItem(
                            label = Strings.get("study_time_today", lang),
                            value = "$todayFocusMins ${Strings.get("min", lang)}",
                            icon = Icons.Default.Timer
                        )
                        StatItem(
                            label = "Target Band",
                            value = "${prefs.targetBandOverall}",
                            icon = Icons.Default.EmojiEvents
                        )
                    }
                }
            }
        }

        // 3. Quick Actions
        item(key = "quick_actions_section") {
            Column {
                Text(
                    text = Strings.get("quick_actions", lang),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.colors.textSecondary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionCard(
                        title = Strings.get("start_focus", lang),
                        icon = Icons.Default.PlayArrow,
                        color = AppTheme.colors.accent,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigateToTab(NavTab.FOCUS)
                    }

                    QuickActionCard(
                        title = Strings.get("add_journal", lang),
                        icon = Icons.Default.EditNote,
                        color = if (AppTheme.colors.isDark) Color(0xFF38BDF8) else SkySecondary,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigateToTab(NavTab.JOURNAL)
                    }

                    QuickActionCard(
                        title = Strings.get("view_vocab", lang),
                        icon = Icons.Default.Translate,
                        color = AppTheme.colors.success,
                        modifier = Modifier.weight(1f)
                    ) {
                        onOpenSecondary(SecondaryScreen.VOCABULARY)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionCard(
                        title = Strings.get("record_test", lang),
                        icon = Icons.Default.FactCheck,
                        color = if (AppTheme.colors.isDark) Color(0xFF93C5FD) else NavyPrimary,
                        modifier = Modifier.weight(1f)
                    ) {
                        onOpenSecondary(SecondaryScreen.PRACTICE_TESTS)
                    }

                    QuickActionCard(
                        title = Strings.get("view_errors", lang),
                        icon = Icons.Default.Troubleshoot,
                        color = AppTheme.colors.error,
                        modifier = Modifier.weight(1f)
                    ) {
                        onOpenSecondary(SecondaryScreen.ERROR_LOG)
                    }

                    QuickActionCard(
                        title = Strings.get("view_assessments", lang),
                        icon = Icons.Default.Rule,
                        color = AppTheme.colors.warning,
                        modifier = Modifier.weight(1f)
                    ) {
                        onOpenSecondary(SecondaryScreen.ASSESSMENTS)
                    }
                }
            }
        }

        // 4. Today's Study Plan Section
        item(key = "today_study_plan_header") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${Strings.get("today_plan", lang)} (Day $activeDayNum)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colors.textPrimary
                    )
                )
                TextButton(
                    onClick = {
                        onSelectDay(activeDayNum)
                        onNavigateToTab(NavTab.PLAN)
                    },
                    modifier = Modifier.testTag("see_all_tasks_button")
                ) {
                    Text(
                        text = Strings.get("continue_plan", lang),
                        color = AppTheme.colors.accent,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = AppTheme.colors.accent,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        if (activeDayWithTasks != null && activeDayWithTasks.tasks.isNotEmpty()) {
            item(key = "active_day_objective_card") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = activeDayWithTasks.day.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.colors.textPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeDayWithTasks.day.learningObjective,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = AppTheme.colors.textSecondary,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }

            // Task list for active day
            items(rootTasks, key = { it.id }, contentType = { "task_row" }) { task ->
                val subtasks = subtasksByParent[task.id] ?: emptyList()
                val isDone = completedTaskIds.contains(task.id)
                TaskItemRow(
                    task = task,
                    isDone = isDone,
                    onToggle = { viewModel.toggleTask(task.id, !isDone) },
                    lang = lang
                )
                if (subtasks.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 4.dp, bottom = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        subtasks.forEach { sub ->
                            val subDone = completedTaskIds.contains(sub.id)
                            TaskItemRow(
                                task = sub,
                                isDone = subDone,
                                onToggle = { viewModel.toggleTask(sub.id, !subDone) },
                                lang = lang,
                                isSubtask = true
                            )
                        }
                    }
                }
            }
        } else {
            item(key = "active_day_empty_card") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Explore the Study Plan to begin Day 1!",
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.textSecondary)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItemRow(
    task: StudyTaskEntity,
    isDone: Boolean,
    onToggle: () -> Unit,
    lang: String,
    isSubtask: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(if (isSubtask) 10.dp else 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isDone && isSubtask -> AppTheme.colors.successContainer.copy(alpha = 0.5f)
                isDone -> AppTheme.colors.successContainer.copy(alpha = 0.65f)
                isSubtask -> AppTheme.colors.surfaceElevated
                else -> AppTheme.colors.surface
            }
        ),
        border = BorderStroke(1.dp, if (isDone) AppTheme.colors.success.copy(alpha = 0.4f) else AppTheme.colors.borderSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("task_item_${task.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = if (isSubtask) 10.dp else 14.dp,
                    vertical = if (isSubtask) 8.dp else 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = AppTheme.colors.success,
                    checkmarkColor = Color.White,
                    uncheckedColor = AppTheme.colors.border
                ),
                modifier = Modifier.testTag("checkbox_${task.id}")
            )

            Spacer(modifier = Modifier.width(if (isSubtask) 6.dp else 10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isSubtask) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = AppTheme.colors.accentContainer,
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = "CHECKLIST",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.colors.accentOnContainer
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = task.title,
                        style = if (isSubtask) MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                            color = if (isDone) AppTheme.colors.textMuted else AppTheme.colors.textPrimary
                        ) else MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                            color = if (isDone) AppTheme.colors.textMuted else AppTheme.colors.textPrimary
                        )
                    )
                }

                if (task.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppTheme.colors.textSecondary,
                            lineHeight = 16.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = AppTheme.colors.surfaceVariant
                    ) {
                        Text(
                            text = task.category,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AppTheme.colors.accent,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${task.estimatedMinutes} ${Strings.get("min", lang)}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AppTheme.colors.textSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
        modifier = modifier
            .clickable { onClick() }
            .height(84.dp)
            .testTag("quick_action_${title.replace(" ", "_").lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = AppTheme.colors.textPrimary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    icon: ImageVector
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppTheme.colors.accent,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.textPrimary
                )
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = AppTheme.colors.textSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}
