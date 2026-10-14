package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.FocusTimerRecordEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

enum class TimerMode(val defaultMinutes: Int) {
    POMODORO(25),
    SHORT_BREAK(5),
    LONG_BREAK(15),
    CUSTOM(30)
}

enum class TimerStatus {
    IDLE, RUNNING, PAUSED
}

@Composable
fun FocusTimerScreen(
    viewModel: MainViewModel,
    lang: String
) {
    val context = LocalContext.current
    val todaySessions by viewModel.todaySessionsCount.collectAsState()
    val todayFocusMinutes by viewModel.todayFocusMinutes.collectAsState()

    var selectedMode by remember { mutableStateOf(TimerMode.POMODORO) }
    var selectedSection by remember { mutableStateOf("Reading") }
    var timerStatus by remember { mutableStateOf(TimerStatus.IDLE) }
    var totalSeconds by remember { mutableIntStateOf(selectedMode.defaultMinutes * 60) }
    var secondsRemaining by remember { mutableIntStateOf(selectedMode.defaultMinutes * 60) }
    var customMinutesInput by remember { mutableStateOf("30") }
    var showCustomDialog by remember { mutableStateOf(false) }

    // Completion note prompt dialog
    var showCompletionDialog by remember { mutableStateOf(false) }
    var completionNote by remember { mutableStateOf("") }
    var finishedSessionMinutes by remember { mutableIntStateOf(0) }
    var isSavingSession by remember { mutableStateOf(false) }

    // Coroutine ticker based on timestamps
    LaunchedEffect(timerStatus) {
        if (timerStatus == TimerStatus.RUNNING) {
            val startTimeMs = System.currentTimeMillis()
            val startRemaining = secondsRemaining

            while (timerStatus == TimerStatus.RUNNING && secondsRemaining > 0) {
                delay(500)
                val elapsedSec = ((System.currentTimeMillis() - startTimeMs) / 1000).toInt()
                val updatedRemaining = (startRemaining - elapsedSec).coerceAtLeast(0)
                secondsRemaining = updatedRemaining

                if (secondsRemaining <= 0) {
                    timerStatus = TimerStatus.IDLE
                    finishedSessionMinutes = totalSeconds / 60

                    // Vibrate alert
                    triggerVibration(context)

                    // Only prompt accomplishments for Focus/Custom sessions, not breaks
                    if (selectedMode == TimerMode.POMODORO || selectedMode == TimerMode.CUSTOM) {
                        showCompletionDialog = true
                    } else {
                        // Just record break
                        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        viewModel.saveFocusRecord(
                            FocusTimerRecordEntity(
                                date = todayStr,
                                mode = selectedMode.name,
                                plannedMinutes = totalSeconds / 60,
                                actualMinutes = totalSeconds / 60,
                                section = selectedSection,
                                notes = "Break finished",
                                isCompleted = true
                            )
                        )
                        // Reset to pomodoro
                        selectedMode = TimerMode.POMODORO
                        totalSeconds = selectedMode.defaultMinutes * 60
                        secondsRemaining = totalSeconds
                    }
                    break
                }
            }
        }
    }

    val sections = listOf("Reading", "Writing Task 2", "Listening", "Speaking", "Writing Task 1", "Vocabulary", "General")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("focus_timer_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Mode selector
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(AppTheme.colors.surfaceVariant)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimerMode.entries.forEach { mode ->
                    val isSelected = selectedMode == mode
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) AppTheme.colors.accent else Color.Transparent,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if (timerStatus == TimerStatus.IDLE) {
                                    if (mode == TimerMode.CUSTOM) {
                                        showCustomDialog = true
                                    } else {
                                        selectedMode = mode
                                        totalSeconds = mode.defaultMinutes * 60
                                        secondsRemaining = totalSeconds
                                    }
                                }
                            }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = when (mode) {
                                    TimerMode.POMODORO -> "Focus"
                                    TimerMode.SHORT_BREAK -> "Short"
                                    TimerMode.LONG_BREAK -> "Long"
                                    TimerMode.CUSTOM -> "Custom"
                                },
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) AppTheme.colors.accentOn else AppTheme.colors.textSecondary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Topic chip selector
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(sections) { sec ->
                    val isChipSelected = selectedSection == sec
                    FilterChip(
                        selected = isChipSelected,
                        onClick = { selectedSection = sec },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = AppTheme.colors.chipBackground,
                            labelColor = AppTheme.colors.chipText,
                            selectedContainerColor = AppTheme.colors.chipSelectedBackground,
                            selectedLabelColor = AppTheme.colors.chipSelectedText
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isChipSelected,
                            borderColor = AppTheme.colors.borderSubtle,
                            selectedBorderColor = AppTheme.colors.accent
                        ),
                        label = {
                            Text(
                                sec,
                                fontSize = 12.sp,
                                fontWeight = if (isChipSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        // Circular Timer Display
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(260.dp)
                .testTag("timer_circle_display")
        ) {
            val progress = if (totalSeconds > 0) (secondsRemaining.toFloat() / totalSeconds.toFloat()) else 0f
            val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")
            val trackColor = AppTheme.colors.progressTrack
            val activeColor = if (selectedMode == TimerMode.POMODORO || selectedMode == TimerMode.CUSTOM) {
                AppTheme.colors.accent
            } else {
                AppTheme.colors.success
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 14.dp.toPx()
                val radius = (size.minDimension - strokeWidth) / 2
                val topLeft = Offset(
                    (size.width - radius * 2) / 2,
                    (size.height - radius * 2) / 2
                )
                val arcSize = Size(radius * 2, radius * 2)

                // Background track
                drawArc(
                    color = trackColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // Foreground active progress
                drawArc(
                    color = activeColor,
                    startAngle = -90f,
                    sweepAngle = animatedProgress * 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val mins = secondsRemaining / 60
                val secs = secondsRemaining % 60
                Text(
                    text = String.format(Locale.getDefault(), "%02d:%02d", mins, secs),
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AppTheme.colors.textPrimary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.colors.surfaceVariant
                ) {
                    Text(
                        text = selectedSection,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AppTheme.colors.accent,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Timer Controls
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset button
                OutlinedIconButton(
                    onClick = {
                        timerStatus = TimerStatus.IDLE
                        secondsRemaining = totalSeconds
                    },
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, AppTheme.colors.border),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = AppTheme.colors.textPrimary
                    ),
                    modifier = Modifier
                        .size(54.dp)
                        .testTag("timer_reset_button")
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = Strings.get("reset", lang),
                        tint = AppTheme.colors.textPrimary
                    )
                }

                // Play / Pause Primary Button
                Button(
                    onClick = {
                        timerStatus = when (timerStatus) {
                            TimerStatus.IDLE -> TimerStatus.RUNNING
                            TimerStatus.RUNNING -> TimerStatus.PAUSED
                            TimerStatus.PAUSED -> TimerStatus.RUNNING
                        }
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.accent,
                        contentColor = AppTheme.colors.accentOn
                    ),
                    modifier = Modifier
                        .size(72.dp)
                        .testTag("timer_main_toggle_button")
                ) {
                    Icon(
                        imageVector = if (timerStatus == TimerStatus.RUNNING) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (timerStatus == TimerStatus.RUNNING) "Pause" else "Start",
                        modifier = Modifier.size(34.dp),
                        tint = AppTheme.colors.accentOn
                    )
                }

                // Skip break / Forward button
                OutlinedIconButton(
                    onClick = {
                        timerStatus = TimerStatus.IDLE
                        if (selectedMode == TimerMode.POMODORO) {
                            selectedMode = TimerMode.SHORT_BREAK
                        } else {
                            selectedMode = TimerMode.POMODORO
                        }
                        totalSeconds = selectedMode.defaultMinutes * 60
                        secondsRemaining = totalSeconds
                    },
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, AppTheme.colors.border),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = AppTheme.colors.textPrimary
                    ),
                    modifier = Modifier
                        .size(54.dp)
                        .testTag("timer_skip_button")
                ) {
                    Icon(
                        Icons.Default.SkipNext,
                        contentDescription = Strings.get("skip", lang),
                        tint = AppTheme.colors.textPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Completed Sessions today card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
                border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AppTheme.colors.success,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Strings.get("sessions_today", lang),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.colors.textPrimary
                            )
                        )
                    }
                    Text(
                        text = "$todaySessions sessions • $todayFocusMinutes ${Strings.get("min", lang)}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.colors.accent
                        )
                    )
                }
            }
        }
    }

    // Custom minutes dialog
    if (showCustomDialog) {
        AlertDialog(
            onDismissRequest = { showCustomDialog = false },
            title = { Text("Custom Focus Duration") },
            text = {
                OutlinedTextField(
                    value = customMinutesInput,
                    onValueChange = { customMinutesInput = it.filter { char -> char.isDigit() } },
                    label = { Text("Minutes") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    val mins = customMinutesInput.toIntOrNull()?.coerceIn(1, 180) ?: 30
                    selectedMode = TimerMode.CUSTOM
                    totalSeconds = mins * 60
                    secondsRemaining = totalSeconds
                    showCustomDialog = false
                }) {
                    Text("Set")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomDialog = false }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }

    // Session accomplishment prompt dialog
    if (showCompletionDialog) {
        AlertDialog(
            onDismissRequest = { showCompletionDialog = false },
            icon = { Icon(Icons.Default.Celebration, contentDescription = null, tint = AmberAccent) },
            title = { Text(Strings.get("session_completed_title", lang)) },
            text = {
                Column {
                    Text(
                        text = Strings.get("session_completed_prompt", lang),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = completionNote,
                        onValueChange = { completionNote = it },
                        placeholder = { Text("e.g. Practiced 10 Matching Headings questions, reviewed synonyms.") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    enabled = !isSavingSession,
                    onClick = {
                        if (isSavingSession) return@Button
                        isSavingSession = true
                        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        viewModel.saveFocusRecord(
                            FocusTimerRecordEntity(
                                date = todayStr,
                                mode = selectedMode.name,
                                plannedMinutes = finishedSessionMinutes.coerceAtLeast(1),
                                actualMinutes = finishedSessionMinutes.coerceAtLeast(1),
                                section = selectedSection,
                                notes = completionNote,
                                isCompleted = true
                            )
                        )
                        // Suggest break
                        selectedMode = TimerMode.SHORT_BREAK
                        totalSeconds = selectedMode.defaultMinutes * 60
                        secondsRemaining = totalSeconds
                        completionNote = ""
                        showCompletionDialog = false
                        isSavingSession = false
                    }
                ) {
                    Text(Strings.get("save_session", lang))
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !isSavingSession,
                    onClick = {
                        if (isSavingSession) return@TextButton
                        isSavingSession = true
                        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        viewModel.saveFocusRecord(
                            FocusTimerRecordEntity(
                                date = todayStr,
                                mode = selectedMode.name,
                                plannedMinutes = finishedSessionMinutes.coerceAtLeast(1),
                                actualMinutes = finishedSessionMinutes.coerceAtLeast(1),
                                section = selectedSection,
                                notes = "",
                                isCompleted = true
                            )
                        )
                        selectedMode = TimerMode.SHORT_BREAK
                        totalSeconds = selectedMode.defaultMinutes * 60
                        secondsRemaining = totalSeconds
                        showCompletionDialog = false
                        isSavingSession = false
                    }
                ) {
                    Text("Skip Note")
                }
            }
        )
    }
}

private fun triggerVibration(context: Context) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator?.vibrate(
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            @Suppress("DEPRECATION")
            vibrator?.vibrate(500)
        }
    } catch (e: Exception) {
        // Safe fallback
    }
}
