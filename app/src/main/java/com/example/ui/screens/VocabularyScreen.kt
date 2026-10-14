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
import com.example.data.local.entity.VocabularyEntryEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val vocabList by viewModel.vocabularyList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") } // "All", "Learning", "Familiar", "Mastered"
    var showAddDialog by remember { mutableStateOf(false) }
    val filterOptions = remember { listOf("All", "Learning", "Familiar", "Mastered") }

    val filteredList = remember(vocabList, searchQuery, selectedFilter) {
        vocabList.filter { item ->
            val matchesSearch = searchQuery.isBlank() ||
                    item.originalWord.contains(searchQuery, ignoreCase = true) ||
                    item.synonyms.contains(searchQuery, ignoreCase = true) ||
                    item.meaning.contains(searchQuery, ignoreCase = true)
            val matchesFilter = when (selectedFilter) {
                "Learning" -> item.masteryStatus == 0
                "Familiar" -> item.masteryStatus == 1
                "Mastered" -> item.masteryStatus == 2
                else -> true
            }
            matchesSearch && matchesFilter
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = AppTheme.colors.accent,
                contentColor = AppTheme.colors.accentOn,
                modifier = Modifier.testTag("add_vocab_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = Strings.get("add_vocab", lang))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("vocab_screen_container")
        ) {
            // Search & Filter
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(Strings.get("search_vocab", lang)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filterOptions.forEach { tag ->
                            FilterChip(
                                selected = selectedFilter == tag,
                                onClick = { selectedFilter = tag },
                                label = { Text(tag, fontSize = 12.sp) }
                            )
                        }
                    }
                    IconButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier.testTag("add_vocab_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = Strings.get("add_vocab", lang),
                            tint = AppTheme.colors.accent
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList, key = { it.id }, contentType = { "vocab_card" }) { item ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.originalWord,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = AppTheme.colors.accent
                                    )
                                )

                                // Mastery toggle chip
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = when (item.masteryStatus) {
                                        2 -> AppTheme.colors.successContainer
                                        1 -> AppTheme.colors.warningContainer
                                        else -> AppTheme.colors.surfaceVariant
                                    },
                                    modifier = Modifier.clickable {
                                        val nextStatus = (item.masteryStatus + 1) % 3
                                        viewModel.saveVocabulary(item.copy(masteryStatus = nextStatus))
                                    }
                                ) {
                                    Text(
                                        text = when (item.masteryStatus) {
                                            2 -> Strings.get("mastery_mastered", lang)
                                            1 -> Strings.get("mastery_familiar", lang)
                                            else -> Strings.get("mastery_learning", lang)
                                        },
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = when (item.masteryStatus) {
                                                2 -> AppTheme.colors.onSuccessContainer
                                                1 -> AppTheme.colors.onWarningContainer
                                                else -> AppTheme.colors.textSecondary
                                            },
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${Strings.get("synonyms", lang)}: ${item.synonyms}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.colors.textPrimary
                                )
                            )

                            if (item.meaning.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.meaning,
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
        var wordInput by remember { mutableStateOf("") }
        var synonymsInput by remember { mutableStateOf("") }
        var meaningInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(Strings.get("add_vocab", lang)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = wordInput,
                        onValueChange = { wordInput = it },
                        label = { Text("Word / Phrase") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = synonymsInput,
                        onValueChange = { synonymsInput = it },
                        label = { Text("Synonyms (comma-separated)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = meaningInput,
                        onValueChange = { meaningInput = it },
                        label = { Text("Meaning / Note") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (wordInput.isNotBlank()) {
                            viewModel.saveVocabulary(
                                VocabularyEntryEntity(
                                    originalWord = wordInput.trim(),
                                    synonyms = synonymsInput.trim(),
                                    meaning = meaningInput.trim(),
                                    topicTag = "Personal"
                                )
                            )
                            showAddDialog = false
                        }
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
