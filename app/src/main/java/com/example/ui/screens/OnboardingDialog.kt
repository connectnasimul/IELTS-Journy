package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.entity.UserPreferencesEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OnboardingDialog(
    currentPrefs: UserPreferencesEntity,
    onComplete: (UserPreferencesEntity) -> Unit
) {
    var language by remember { mutableStateOf(currentPrefs.language) }
    var ieltsType by remember { mutableStateOf(currentPrefs.ieltsType) }
    var targetOverall by remember { mutableStateOf(currentPrefs.targetBandOverall.toString()) }
    var targetListening by remember { mutableStateOf(currentPrefs.targetBandListening.toString()) }
    var targetReading by remember { mutableStateOf(currentPrefs.targetBandReading.toString()) }
    var targetWriting by remember { mutableStateOf(currentPrefs.targetBandWriting.toString()) }
    var targetSpeaking by remember { mutableStateOf(currentPrefs.targetBandSpeaking.toString()) }

    val todayStr = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
    var startDate by remember { mutableStateOf(if (currentPrefs.studyStartDate.isBlank()) todayStr else currentPrefs.studyStartDate) }
    var testDate by remember { mutableStateOf(currentPrefs.targetTestDate) }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.88f)
                .padding(8.dp)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Welcome to IELTS Journey",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = AppTheme.colors.textPrimary)
                )
                Text(
                    text = Strings.get("app_tagline", language),
                    style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text("Interface Language", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 4.dp)) {
                            FilterChip(
                                selected = language == "en",
                                onClick = { language = "en" },
                                label = { Text("English") }
                            )
                            FilterChip(
                                selected = language == "bn",
                                onClick = { language = "bn" },
                                label = { Text("বাংলা (Bangla)") }
                            )
                        }
                    }

                    item {
                        Text("IELTS Module", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 4.dp)) {
                            FilterChip(
                                selected = ieltsType == "Academic",
                                onClick = { ieltsType = "Academic" },
                                label = { Text("Academic") }
                            )
                            FilterChip(
                                selected = ieltsType == "General Training",
                                onClick = { ieltsType = "General Training" },
                                label = { Text("General Training") }
                            )
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = { Text("Plan Start Date (YYYY-MM-DD)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = testDate,
                            onValueChange = { testDate = it },
                            label = { Text("Target Test Date (Optional)") },
                            placeholder = { Text("e.g. 2026-11-20") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Text("Target Band Scores", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                        OutlinedTextField(
                            value = targetOverall,
                            onValueChange = { targetOverall = it },
                            label = { Text("Overall Target (e.g. 7.0)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = targetListening,
                                onValueChange = { targetListening = it },
                                label = { Text("Listening") },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = targetReading,
                                onValueChange = { targetReading = it },
                                label = { Text("Reading") },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = targetWriting,
                                onValueChange = { targetWriting = it },
                                label = { Text("Writing") },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = targetSpeaking,
                                onValueChange = { targetSpeaking = it },
                                label = { Text("Speaking") },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        val overall = targetOverall.toDoubleOrNull() ?: 7.0
                        val l = targetListening.toDoubleOrNull() ?: 7.5
                        val r = targetReading.toDoubleOrNull() ?: 7.0
                        val w = targetWriting.toDoubleOrNull() ?: 6.5
                        val s = targetSpeaking.toDoubleOrNull() ?: 7.0

                        onComplete(
                            currentPrefs.copy(
                                language = language,
                                ieltsType = ieltsType,
                                targetBandOverall = overall,
                                targetBandListening = l,
                                targetBandReading = r,
                                targetBandWriting = w,
                                targetBandSpeaking = s,
                                studyStartDate = startDate,
                                targetTestDate = testDate,
                                isOnboarded = true
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.accent,
                        contentColor = AppTheme.colors.accentOn
                    )
                ) {
                    Text("Start My IELTS Journey")
                }
            }
        }
    }
}
