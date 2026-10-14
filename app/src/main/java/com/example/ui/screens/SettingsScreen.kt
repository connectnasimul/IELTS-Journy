package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.UserPreferencesEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val prefs by viewModel.userPreferences.collectAsState()

    var ieltsType by remember(prefs) { mutableStateOf(prefs.ieltsType) }
    var targetOverall by remember(prefs) { mutableStateOf(prefs.targetBandOverall.toString()) }
    var targetListening by remember(prefs) { mutableStateOf(prefs.targetBandListening.toString()) }
    var targetReading by remember(prefs) { mutableStateOf(prefs.targetBandReading.toString()) }
    var targetWriting by remember(prefs) { mutableStateOf(prefs.targetBandWriting.toString()) }
    var targetSpeaking by remember(prefs) { mutableStateOf(prefs.targetBandSpeaking.toString()) }
    var startDate by remember(prefs) { mutableStateOf(prefs.studyStartDate) }
    var testDate by remember(prefs) { mutableStateOf(prefs.targetTestDate) }

    // Dialogs
    var showExportDialog by remember { mutableStateOf(false) }
    var exportedJson by remember { mutableStateOf("") }
    var showImportDialog by remember { mutableStateOf(false) }
    var importJsonText by remember { mutableStateOf("") }

    var showResetTasksConfirm by remember { mutableStateOf(false) }
    var showResetAllConfirm by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("settings_screen_scroll"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            // 1. Language & IELTS Type
            item(key = "settings_language", contentType = "language") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = Strings.get("interface_language", lang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            FilterChip(
                                selected = lang == "en",
                                onClick = { viewModel.setLanguage("en") },
                                label = { Text("English (Default)") }
                            )
                            FilterChip(
                                selected = lang == "bn",
                                onClick = { viewModel.setLanguage("bn") },
                                label = { Text("বাংলা (Bangla)") }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "IELTS Module Type",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            FilterChip(
                                selected = ieltsType == "Academic",
                                onClick = {
                                    ieltsType = "Academic"
                                    viewModel.updatePreferences(prefs.copy(ieltsType = "Academic"))
                                },
                                label = { Text("Academic") }
                            )
                            FilterChip(
                                selected = ieltsType == "General Training",
                                onClick = {
                                    ieltsType = "General Training"
                                    viewModel.updatePreferences(prefs.copy(ieltsType = "General Training"))
                                },
                                label = { Text("General Training") }
                            )
                        }
                    }
                }
            }

            // 2. Schedule & Target Dates
            item(key = "settings_schedule", contentType = "schedule") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Study Dates & Scheduling",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = startDate,
                            onValueChange = {
                                startDate = it
                                viewModel.updatePreferences(prefs.copy(studyStartDate = it))
                            },
                            label = { Text(Strings.get("start_date", lang)) },
                            placeholder = { Text("YYYY-MM-DD (e.g. 2026-10-10)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = testDate,
                            onValueChange = {
                                testDate = it
                                viewModel.updatePreferences(prefs.copy(targetTestDate = it))
                            },
                            label = { Text(Strings.get("test_date", lang)) },
                            placeholder = { Text("YYYY-MM-DD (e.g. 2026-11-21)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Changing the start date recalculates your active study day without resetting any completed tasks.",
                            style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                        )
                    }
                }
            }

            // 3. Target Band Scores
            item(key = "settings_target_scores", contentType = "target_scores") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = Strings.get("target_scores", lang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = targetOverall,
                            onValueChange = {
                                targetOverall = it
                                val band = it.toDoubleOrNull() ?: prefs.targetBandOverall
                                viewModel.updatePreferences(prefs.copy(targetBandOverall = band))
                            },
                            label = { Text("Overall Target Band") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = targetListening,
                                onValueChange = {
                                    targetListening = it
                                    val b = it.toDoubleOrNull() ?: prefs.targetBandListening
                                    viewModel.updatePreferences(prefs.copy(targetBandListening = b))
                                },
                                label = { Text("Listening") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = targetReading,
                                onValueChange = {
                                    targetReading = it
                                    val b = it.toDoubleOrNull() ?: prefs.targetBandReading
                                    viewModel.updatePreferences(prefs.copy(targetBandReading = b))
                                },
                                label = { Text("Reading") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = targetWriting,
                                onValueChange = {
                                    targetWriting = it
                                    val b = it.toDoubleOrNull() ?: prefs.targetBandWriting
                                    viewModel.updatePreferences(prefs.copy(targetBandWriting = b))
                                },
                                label = { Text("Writing") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = targetSpeaking,
                                onValueChange = {
                                    targetSpeaking = it
                                    val b = it.toDoubleOrNull() ?: prefs.targetBandSpeaking
                                    viewModel.updatePreferences(prefs.copy(targetBandSpeaking = b))
                                },
                                label = { Text("Speaking") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            // 4. Data Backup & Restore
            item(key = "settings_backup", contentType = "backup") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Offline Data & Backup",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "All data is securely stored on your device. Export as JSON to save or transfer to another device.",
                            style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.exportBackup { json ->
                                        exportedJson = json
                                        showExportDialog = true
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Export JSON")
                            }

                            OutlinedButton(
                                onClick = { showImportDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Upload, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Import JSON")
                            }
                        }
                    }
                }
            }

            // 5. Danger Zone / Resets
            item(key = "settings_danger_zone") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.colors.errorContainer.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = Strings.get("danger_zone", lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.colors.error
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Reset All Tasks
                        OutlinedButton(
                            onClick = { showResetTasksConfirm = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AppTheme.colors.error),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(Strings.get("reset_tasks_title", lang))
                        }
                        Text(
                            text = Strings.get("reset_tasks_desc", lang),
                            style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary),
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                        )

                        // Clear Focus History
                        OutlinedButton(
                            onClick = {
                                viewModel.clearFocusHistory {
                                    Toast.makeText(context, "Timer records cleared", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Clear Focus Timer Records")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Reset Entire App
                        Button(
                            onClick = { showResetAllConfirm = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.error,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.DeleteForever, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(Strings.get("reset_all_data", lang))
                        }
                    }
                }
            }
        }

    // Export Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text(Strings.get("backup_export", lang)) },
            text = {
                Column {
                    Text("Copy your backup JSON data:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = exportedJson,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("IELTS Journey Backup", exportedJson))
                    Toast.makeText(context, "Backup copied to clipboard!", Toast.LENGTH_SHORT).show()
                    showExportDialog = false
                }) {
                    Text("Copy to Clipboard")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Import Dialog
    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text(Strings.get("backup_import", lang)) },
            text = {
                Column {
                    Text("Paste your valid IELTS Journey backup JSON:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = importJsonText,
                        onValueChange = { importJsonText = it },
                        placeholder = { Text("{\"version\": 1, ...}") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.importBackup(importJsonText) { success ->
                        if (success) {
                            Toast.makeText(context, "Backup restored successfully!", Toast.LENGTH_SHORT).show()
                            showImportDialog = false
                        } else {
                            Toast.makeText(context, "Invalid backup format!", Toast.LENGTH_LONG).show()
                        }
                    }
                }) {
                    Text("Restore")
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }

    // Reset Tasks Confirmation
    if (showResetTasksConfirm) {
        AlertDialog(
            onDismissRequest = { showResetTasksConfirm = false },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = AmberAccent) },
            title = { Text("Reset All Tasks?") },
            text = { Text("This will uncheck all tasks across all 6 phases. Your journal entries, notes, and practice test records will NOT be deleted.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetAllTasks {
                            showResetTasksConfirm = false
                            Toast.makeText(context, Strings.get("reset_confirmed", lang), Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.error)
                ) {
                    Text("Uncheck All Tasks")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetTasksConfirm = false }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }

    // Reset All App Data Confirmation
    if (showResetAllConfirm) {
        AlertDialog(
            onDismissRequest = { showResetAllConfirm = false },
            icon = { Icon(Icons.Default.DeleteForever, contentDescription = null, tint = AppTheme.colors.error) },
            title = { Text("Reset Entire Application?") },
            text = { Text("Warning: This will permanently delete all task completion, journal entries, practice tests, and custom notes, returning the app to initial state. This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetEntireApp {
                            showResetAllConfirm = false
                            Toast.makeText(context, "Application reset complete", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.error)
                ) {
                    Text("Reset Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetAllConfirm = false }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }
}
