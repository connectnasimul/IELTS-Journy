package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.StudyDayEntity
import com.example.data.local.entity.StudyTaskEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.DayWithTasks
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.PhaseWithDays

enum class TaskFilter {
    ALL, INCOMPLETE, COMPLETED, TODAY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyPlanScreen(
    viewModel: MainViewModel,
    lang: String
) {
    val phasesWithDays by viewModel.phasesWithDays.collectAsState()
    val completedTaskIds by viewModel.completedTaskIds.collectAsState()
    val activeDayNumber by viewModel.currentActiveDayNumber.collectAsState()
    val selectedDayNum by viewModel.selectedDayNumber.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var currentFilter by remember { mutableStateOf(TaskFilter.ALL) }
    var expandedPhaseId by remember { mutableStateOf<Int?>(1) }
    var expandedDayId by remember { mutableStateOf<Int?>(selectedDayNum ?: activeDayNumber) }

    // Dialog states for reset
    var phaseToReset by remember { mutableStateOf<PhaseWithDays?>(null) }
    var dayToReset by remember { mutableStateOf<DayWithTasks?>(null) }

    // Update expanded day if selectedDayNum changes externally (e.g. from Home)
    LaunchedEffect(selectedDayNum) {
        if (selectedDayNum != null) {
            expandedDayId = selectedDayNum
            val targetPhase = phasesWithDays.find { p -> p.days.any { it.day.dayNumber == selectedDayNum } }
            if (targetPhase != null) {
                expandedPhaseId = targetPhase.phase.id
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.surface)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(Strings.get("search_tasks", lang), color = AppTheme.colors.textMuted) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AppTheme.colors.iconSecondary) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = AppTheme.colors.iconSecondary)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_tasks_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppTheme.colors.accent,
                        unfocusedBorderColor = AppTheme.colors.borderSubtle,
                        focusedTextColor = AppTheme.colors.textPrimary,
                        unfocusedTextColor = AppTheme.colors.textPrimary,
                        cursorColor = AppTheme.colors.accent
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Responsive scrollable filter chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filterItems = remember(lang) {
                        listOf(
                            TaskFilter.ALL to Strings.get("filter_all", lang),
                            TaskFilter.INCOMPLETE to Strings.get("filter_incomplete", lang),
                            TaskFilter.COMPLETED to Strings.get("filter_completed", lang),
                            TaskFilter.TODAY to Strings.get("filter_today", lang)
                        )
                    }

                    filterItems.forEach { (filter, labelText) ->
                        val isSelected = currentFilter == filter
                        FilterChip(
                            selected = isSelected,
                            onClick = { currentFilter = filter },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = AppTheme.colors.chipBackground,
                                labelColor = AppTheme.colors.chipText,
                                selectedContainerColor = AppTheme.colors.chipSelectedBackground,
                                selectedLabelColor = AppTheme.colors.chipSelectedText
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = AppTheme.colors.borderSubtle,
                                selectedBorderColor = AppTheme.colors.accent
                            ),
                            label = {
                                Text(
                                    labelText,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            modifier = Modifier.testTag("filter_chip_${filter.name.lowercase()}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("study_plan_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            phasesWithDays.forEach { phaseWithDays ->
                val isPhaseExpanded = expandedPhaseId == phaseWithDays.phase.id

                item(
                    key = "phase_${phaseWithDays.phase.id}",
                    contentType = "phase_header"
                ) {
                    PhaseHeaderCard(
                        phaseWithDays = phaseWithDays,
                        isExpanded = isPhaseExpanded,
                        lang = lang,
                        onToggleExpandPhase = {
                            expandedPhaseId = if (isPhaseExpanded) null else phaseWithDays.phase.id
                        },
                        onResetPhase = { phaseToReset = phaseWithDays }
                    )
                }

                if (isPhaseExpanded) {
                    item(
                        key = "phase_goal_${phaseWithDays.phase.id}",
                        contentType = "phase_goal"
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AppTheme.colors.surfaceVariant,
                            border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = phaseWithDays.phase.goal,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = AppTheme.colors.accent,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            )
                        }
                    }

                    val filteredDays = if (currentFilter == TaskFilter.TODAY) {
                        phaseWithDays.days.filter { it.day.dayNumber == activeDayNumber }
                    } else {
                        phaseWithDays.days
                    }

                    items(
                        items = filteredDays,
                        key = { "day_${it.day.id}" },
                        contentType = { "day_accordion" }
                    ) { dayWithTasks ->
                        val isDayToday = dayWithTasks.day.dayNumber == activeDayNumber
                        val isDayExpanded = expandedDayId == dayWithTasks.day.id

                        DayAccordionItem(
                            dayWithTasks = dayWithTasks,
                            isExpanded = isDayExpanded,
                            isToday = isDayToday,
                            lang = lang,
                            searchQuery = searchQuery,
                            currentFilter = currentFilter,
                            completedTaskIds = completedTaskIds,
                            onToggleExpand = {
                                expandedDayId = if (expandedDayId == dayWithTasks.day.id) null else dayWithTasks.day.id
                            },
                            onToggleTask = { taskId, isDone ->
                                viewModel.toggleTask(taskId, isDone)
                            },
                            onResetDay = { dayToReset = dayWithTasks }
                        )
                    }
                }
            }
        }
    }

    // Reset Phase Dialog
    if (phaseToReset != null) {
        val target = phaseToReset!!
        AlertDialog(
            onDismissRequest = { phaseToReset = null },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = AmberAccent) },
            title = { Text(text = "Reset ${target.phase.title}?") },
            text = { Text("This will mark all tasks in this phase as incomplete. Your journal entries and practice tests will NOT be deleted.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetPhaseTasks(target.phase.id) {
                            phaseToReset = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.error)
                ) {
                    Text("Reset Phase")
                }
            },
            dismissButton = {
                TextButton(onClick = { phaseToReset = null }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }

    // Reset Day Dialog
    if (dayToReset != null) {
        val target = dayToReset!!
        AlertDialog(
            onDismissRequest = { dayToReset = null },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = AppTheme.colors.warning) },
            title = { Text(text = "Reset Day ${target.day.dayNumber}?") },
            text = { Text("This will mark all tasks in Day ${target.day.dayNumber} as incomplete.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetDayTasks(target.day.id) {
                            dayToReset = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.error)
                ) {
                    Text("Reset Day")
                }
            },
            dismissButton = {
                TextButton(onClick = { dayToReset = null }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }
}

@Composable
fun PhaseHeaderCard(
    phaseWithDays: PhaseWithDays,
    isExpanded: Boolean,
    lang: String,
    onToggleExpandPhase: () -> Unit,
    onResetPhase: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        border = BorderStroke(1.dp, if (isExpanded) AppTheme.colors.accent.copy(alpha = 0.6f) else AppTheme.colors.borderSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("phase_card_${phaseWithDays.phase.id}")
            .clickable { onToggleExpandPhase() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = phaseWithDays.phase.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colors.textPrimary
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Days ${phaseWithDays.phase.dayStart}–${phaseWithDays.phase.dayEnd} • ${phaseWithDays.completedTaskCount}/${phaseWithDays.totalTaskCount} ${Strings.get("tasks_done", lang)}",
                    style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                )
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { phaseWithDays.progressPercent / 100f },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = AppTheme.colors.progressIndicator,
                    trackColor = AppTheme.colors.progressTrack
                )
            }

            IconButton(onClick = onResetPhase) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset Phase",
                    tint = AppTheme.colors.iconSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = AppTheme.colors.iconSecondary
            )
        }
    }
}

@Composable
fun DayAccordionItem(
    dayWithTasks: DayWithTasks,
    isExpanded: Boolean,
    isToday: Boolean,
    lang: String,
    searchQuery: String,
    currentFilter: TaskFilter,
    completedTaskIds: Set<String>,
    onToggleExpand: () -> Unit,
    onToggleTask: (String, Boolean) -> Unit,
    onResetDay: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isToday) AppTheme.colors.accentContainer.copy(alpha = 0.45f) else AppTheme.colors.surfaceVariant
        ),
        border = BorderStroke(1.dp, if (isToday) AppTheme.colors.accent.copy(alpha = 0.5f) else AppTheme.colors.borderSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("day_item_${dayWithTasks.day.dayNumber}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Day Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (dayWithTasks.progressPercent == 100) AppTheme.colors.success else AppTheme.colors.accent,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${dayWithTasks.day.dayNumber}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = if (dayWithTasks.progressPercent == 100) Color.White else AppTheme.colors.accentOn,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = dayWithTasks.day.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.colors.textPrimary
                            )
                        )
                        if (isToday) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = AppTheme.colors.warning
                            ) {
                                Text(
                                    text = "TODAY",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.Black,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${dayWithTasks.completedTaskCount}/${dayWithTasks.totalTaskCount} completed • ${dayWithTasks.day.recommendedDurationMinutes} ${Strings.get("min", lang)}",
                        style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                    )
                }

                IconButton(
                    onClick = onResetDay,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset Day",
                        tint = AppTheme.colors.iconSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.colors.iconSecondary
                )
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Learning Objective
                    Text(
                        text = dayWithTasks.day.learningObjective,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppTheme.colors.textSecondary,
                            lineHeight = 18.sp
                        )
                    )

                    // Examiner Tip (if available)
                    if (dayWithTasks.day.examinerTip.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AppTheme.colors.warningContainer.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = AppTheme.colors.warning,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = Strings.get("examiner_tip", lang),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = AppTheme.colors.onWarningContainer
                                        )
                                    )
                                    Text(
                                        text = dayWithTasks.day.examinerTip,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = AppTheme.colors.textPrimary,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Common Mistake (if available)
                    if (dayWithTasks.day.commonMistake.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AppTheme.colors.errorContainer.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = AppTheme.colors.error,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = Strings.get("common_mistake", lang),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = AppTheme.colors.onErrorContainer
                                        )
                                    )
                                    Text(
                                        text = dayWithTasks.day.commonMistake,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = AppTheme.colors.textPrimary,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Reflection Questions (if available)
                    if (dayWithTasks.day.reflectionQuestions.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AppTheme.colors.surfaceElevated,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = Strings.get("reflection_questions", lang),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = AppTheme.colors.accent
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = dayWithTasks.day.reflectionQuestions,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = AppTheme.colors.textSecondary,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Pre-compute visible tasks once per state change to avoid string matching during draw
                    val visibleItems = remember(dayWithTasks.tasks, completedTaskIds, searchQuery, currentFilter) {
                        val roots = dayWithTasks.tasks.filter { it.parentTaskId == null }
                        val subMap = dayWithTasks.tasks.filter { it.parentTaskId != null }.groupBy { it.parentTaskId }

                        roots.mapNotNull { task ->
                            val subtasks = subMap[task.id] ?: emptyList()
                            val isDone = completedTaskIds.contains(task.id)

                            val matchesSearch = searchQuery.isBlank() ||
                                task.title.contains(searchQuery, ignoreCase = true) ||
                                task.description.contains(searchQuery, ignoreCase = true) ||
                                subtasks.any {
                                    it.title.contains(searchQuery, ignoreCase = true) ||
                                    it.description.contains(searchQuery, ignoreCase = true)
                                }

                            if (!matchesSearch) return@mapNotNull null

                            val allSubtasksDone = subtasks.isNotEmpty() && subtasks.all { completedTaskIds.contains(it.id) }
                            val noSubtasksDone = subtasks.isNotEmpty() && subtasks.none { completedTaskIds.contains(it.id) }

                            val skipRootByFilter = when (currentFilter) {
                                TaskFilter.INCOMPLETE -> isDone && (subtasks.isEmpty() || allSubtasksDone)
                                TaskFilter.COMPLETED -> !isDone && (subtasks.isEmpty() || noSubtasksDone)
                                else -> false
                            }

                            val visibleSubs = subtasks.mapNotNull { sub ->
                                val subDone = completedTaskIds.contains(sub.id)
                                if (currentFilter == TaskFilter.INCOMPLETE && subDone) return@mapNotNull null
                                if (currentFilter == TaskFilter.COMPLETED && !subDone) return@mapNotNull null
                                if (searchQuery.isNotBlank() &&
                                    !sub.title.contains(searchQuery, ignoreCase = true) &&
                                    !sub.description.contains(searchQuery, ignoreCase = true) &&
                                    !task.title.contains(searchQuery, ignoreCase = true)
                                ) return@mapNotNull null
                                sub to subDone
                            }

                            if (skipRootByFilter && visibleSubs.isEmpty()) return@mapNotNull null

                            VisibleTaskEntry(
                                task = task,
                                isDone = isDone,
                                showRoot = !skipRootByFilter,
                                visibleSubtasks = visibleSubs
                            )
                        }
                    }

                    visibleItems.forEach { item ->
                        if (item.showRoot) {
                            TaskItemRow(
                                task = item.task,
                                isDone = item.isDone,
                                onToggle = { onToggleTask(item.task.id, !item.isDone) },
                                lang = lang,
                                isSubtask = false
                            )
                        }

                        if (item.visibleSubtasks.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, top = 4.dp, bottom = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                item.visibleSubtasks.forEach { (sub, subDone) ->
                                    TaskItemRow(
                                        task = sub,
                                        isDone = subDone,
                                        onToggle = { onToggleTask(sub.id, !subDone) },
                                        lang = lang,
                                        isSubtask = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class VisibleTaskEntry(
    val task: StudyTaskEntity,
    val isDone: Boolean,
    val showRoot: Boolean,
    val visibleSubtasks: List<Pair<StudyTaskEntity, Boolean>>
)
