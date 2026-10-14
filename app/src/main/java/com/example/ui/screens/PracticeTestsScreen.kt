package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import com.example.data.local.entity.PracticeTestEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeTestsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val tests by viewModel.practiceTests.collectAsState()
    var selectedSkillFilter by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }

    val skillsList = remember {
        listOf("All", "Reading", "Listening", "Writing", "Speaking")
    }

    val filteredTests = remember(tests, selectedSkillFilter) {
        tests.filter {
            selectedSkillFilter == "All" || it.section.contains(selectedSkillFilter, ignoreCase = true)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = AppTheme.colors.accent,
                contentColor = AppTheme.colors.accentOn,
                modifier = Modifier.testTag("add_test_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = Strings.get("add_test", lang))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("practice_tests_screen_container")
        ) {
            // Filter row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(skillsList, key = { it }) { skill ->
                        FilterChip(
                            selected = selectedSkillFilter == skill,
                            onClick = { selectedSkillFilter = skill },
                            label = { Text(skill, fontSize = 12.sp) }
                        )
                    }
                }
                IconButton(
                    onClick = { showAddDialog = true },
                    modifier = Modifier.testTag("add_test_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = Strings.get("add_test", lang),
                        tint = AppTheme.colors.accent
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredTests, key = { it.id }, contentType = { "practice_card" }) { test ->
                    val accuracy = if (test.totalQuestions > 0) (test.correctAnswers * 100) / test.totalQuestions else 0

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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = AppTheme.colors.accentContainer
                                    ) {
                                        Text(
                                            text = test.section,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = AppTheme.colors.accentOnContainer,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }

                                    if (test.isHistoricalReference) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = AppTheme.colors.surfaceVariant
                                        ) {
                                            Text(
                                                text = Strings.get("historical_ref", lang),
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = AppTheme.colors.textSecondary,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Medium
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = test.testDate,
                                    style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${test.sourceBook} - ${test.testNumber}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.colors.textPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                if (test.totalQuestions > 0) {
                                    Text(
                                        text = "${Strings.get("raw_score", lang)}: ${test.correctAnswers}/${test.totalQuestions} ($accuracy%)",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = AppTheme.colors.textPrimary
                                        )
                                    )
                                }
                                if (test.estimatedBand > 0) {
                                    Text(
                                        text = "${Strings.get("estimated_band", lang)}: ${test.estimatedBand}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = AppTheme.colors.accent
                                        )
                                    )
                                }
                            }

                            if (test.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = test.notes,
                                    style = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        val todayStr = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
        var section by remember { mutableStateOf("Reading") }
        var sourceBook by remember { mutableStateOf("Cambridge IELTS 18") }
        var testNumber by remember { mutableStateOf("Test 1") }
        var correctAnswersText by remember { mutableStateOf("28") }
        var totalQuestionsText by remember { mutableStateOf("40") }
        var bandText by remember { mutableStateOf("6.5") }
        var timeTakenText by remember { mutableStateOf("60") }
        var notes by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(Strings.get("add_test", lang)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Reading", "Listening", "Writing", "Speaking").forEach { s ->
                            FilterChip(
                                selected = section == s,
                                onClick = { section = s },
                                label = { Text(s, fontSize = 11.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = sourceBook,
                        onValueChange = { sourceBook = it },
                        label = { Text("Book / Source") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = testNumber,
                        onValueChange = { testNumber = it },
                        label = { Text("Test Number / Identifier") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = correctAnswersText,
                            onValueChange = { correctAnswersText = it.filter { c -> c.isDigit() } },
                            label = { Text("Correct") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = totalQuestionsText,
                            onValueChange = { totalQuestionsText = it.filter { c -> c.isDigit() } },
                            label = { Text("Total Qs") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = bandText,
                            onValueChange = { bandText = it },
                            label = { Text("Band") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes / Mistakes Pattern") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val correct = correctAnswersText.toIntOrNull() ?: 0
                        val total = totalQuestionsText.toIntOrNull() ?: 40
                        val band = bandText.toDoubleOrNull() ?: 0.0

                        viewModel.savePracticeTest(
                            PracticeTestEntity(
                                testDate = todayStr,
                                section = section,
                                sourceBook = sourceBook.trim(),
                                testNumber = testNumber.trim(),
                                correctAnswers = correct,
                                totalQuestions = total,
                                estimatedBand = band,
                                timeTakenMinutes = timeTakenText.toIntOrNull() ?: 60,
                                notes = notes.trim(),
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
