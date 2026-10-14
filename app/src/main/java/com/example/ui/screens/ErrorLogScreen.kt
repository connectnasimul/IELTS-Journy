package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.data.local.entity.ErrorLogEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorLogScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val errorLogs by viewModel.errorLogs.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }

    val categories = remember {
        listOf(
            "All",
            "Word-Grabbing Without Context",
            "Confusing Mentioned with Answer",
            "Distractor Trap",
            "Using Assumptions Instead of Evidence",
            "Not Understanding Question Types",
            "Time Management Problems"
        )
    }

    val filteredLogs = remember(errorLogs, selectedCategoryFilter) {
        errorLogs.filter {
            selectedCategoryFilter == "All" || it.mistakeCategory.equals(selectedCategoryFilter, ignoreCase = true)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = AppTheme.colors.accent,
                contentColor = AppTheme.colors.accentOn,
                modifier = Modifier.testTag("add_error_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = Strings.get("add_error", lang))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("error_log_screen_container")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(categories, key = { it }) { cat ->
                        FilterChip(
                            selected = selectedCategoryFilter == cat,
                            onClick = { selectedCategoryFilter = cat },
                            label = { Text(cat, fontSize = 11.sp) }
                        )
                    }
                }
                IconButton(
                    onClick = { showAddDialog = true },
                    modifier = Modifier.testTag("add_error_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = Strings.get("add_error", lang),
                        tint = AppTheme.colors.accent
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredLogs, key = { it.id }, contentType = { "error_card" }) { log ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = AppTheme.colors.errorContainer
                                ) {
                                    Text(
                                        text = log.mistakeCategory,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = AppTheme.colors.onErrorContainer,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                // Review Status button
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = when (log.reviewStatus) {
                                        2 -> AppTheme.colors.successContainer
                                        1 -> AppTheme.colors.warningContainer
                                        else -> AppTheme.colors.surfaceVariant
                                    },
                                    modifier = Modifier.clickable {
                                        val nextStatus = (log.reviewStatus + 1) % 3
                                        viewModel.saveErrorLog(log.copy(reviewStatus = nextStatus))
                                    }
                                ) {
                                    Text(
                                        text = when (log.reviewStatus) {
                                            2 -> "Mastered"
                                            1 -> "Understood"
                                            else -> "Pending Review"
                                        },
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = when (log.reviewStatus) {
                                                2 -> AppTheme.colors.onSuccessContainer
                                                1 -> AppTheme.colors.onWarningContainer
                                                else -> AppTheme.colors.textSecondary
                                            },
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${log.testReference} - ${log.questionNumber} (${log.questionType})",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "${Strings.get("my_answer", lang)}: ${log.originalAnswer}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.error, fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = "${Strings.get("correct_answer", lang)}: ${log.correctAnswer}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.success, fontWeight = FontWeight.Bold)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Why: ${log.reasonForError}",
                                style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AppTheme.colors.surfaceElevated,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = Strings.get("strategy_avoid", lang),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = AppTheme.colors.accent)
                                    )
                                    Text(
                                        text = log.strategyToAvoid,
                                        style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textPrimary)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        val todayStr = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
        var testRef by remember { mutableStateOf("Cambridge 18 Test 1") }
        var qNum by remember { mutableStateOf("Q15") }
        var qType by remember { mutableStateOf("Matching Headings") }
        var originalAns by remember { mutableStateOf("") }
        var correctAns by remember { mutableStateOf("") }
        var reason by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("Distractor Trap") }
        var strategy by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(Strings.get("add_error", lang)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = testRef,
                        onValueChange = { testRef = it },
                        label = { Text("Test / Passage Reference") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = qNum,
                            onValueChange = { qNum = it },
                            label = { Text("Question #") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = qType,
                            onValueChange = { qType = it },
                            label = { Text("Question Type") },
                            singleLine = true,
                            modifier = Modifier.weight(1.5f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = originalAns,
                            onValueChange = { originalAns = it },
                            label = { Text("My Answer") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = correctAns,
                            onValueChange = { correctAns = it },
                            label = { Text("Correct Answer") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Mistake Category") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        label = { Text("Why I got it wrong") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = strategy,
                        onValueChange = { strategy = it },
                        label = { Text("Strategy to avoid next time") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveErrorLog(
                            ErrorLogEntity(
                                date = todayStr,
                                section = "Reading",
                                testReference = testRef.trim(),
                                questionNumber = qNum.trim(),
                                questionType = qType.trim(),
                                originalAnswer = originalAns.trim(),
                                correctAnswer = correctAns.trim(),
                                reasonForError = reason.trim(),
                                mistakeCategory = category.trim(),
                                strategyToAvoid = strategy.trim(),
                                reviewStatus = 0,
                                isHistoricalReference = false
                            )
                        )
                        showAddDialog = false
                    }
                ) {
                    Text(Strings.get("save", lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }
}
