package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.entity.JournalEntryEntity
import com.example.ui.theme.*
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: MainViewModel,
    lang: String
) {
    val entries by viewModel.journalEntries.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedSectionFilter by remember { mutableStateOf<String?>(null) }
    var entryToEdit by remember { mutableStateOf<JournalEntryEntity?>(null) }
    var isCreatingNew by remember { mutableStateOf(false) }
    var entryToDelete by remember { mutableStateOf<JournalEntryEntity?>(null) }

    val sections = remember {
        listOf("All", "Reading", "Writing Task 2", "Listening", "Speaking", "Writing Task 1", "Vocabulary", "Grammar", "Mock Test")
    }

    val filteredEntries = remember(entries, searchQuery, selectedSectionFilter) {
        entries.filter { entry ->
            val matchesSearch = searchQuery.isBlank() ||
                    entry.title.contains(searchQuery, ignoreCase = true) ||
                    entry.content.contains(searchQuery, ignoreCase = true) ||
                    entry.whatLearned.contains(searchQuery, ignoreCase = true) ||
                    entry.tags.contains(searchQuery, ignoreCase = true)
            val matchesSection = selectedSectionFilter == null || selectedSectionFilter == "All" || entry.section.equals(selectedSectionFilter, ignoreCase = true)
            matchesSearch && matchesSection
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isCreatingNew = true },
                containerColor = AppTheme.colors.accent,
                contentColor = AppTheme.colors.accentOn,
                modifier = Modifier.testTag("add_journal_entry_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = Strings.get("new_entry", lang))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("journal_screen_container")
        ) {
            // Search and Filter Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(Strings.get("search_journal", lang)) },
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

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(sections) { sec ->
                        val isSelected = (sec == "All" && selectedSectionFilter == null) || selectedSectionFilter == sec
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSectionFilter = if (sec == "All") null else sec },
                            label = { Text(sec, fontSize = 12.sp) }
                        )
                    }
                }
            }

            if (filteredEntries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
                            tint = AppTheme.colors.iconSecondary,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = Strings.get("empty_journal", lang),
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.textSecondary),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("journal_entries_list"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredEntries, key = { it.id }, contentType = { "journal_entry" }) { entry ->
                        JournalEntryCard(
                            entry = entry,
                            lang = lang,
                            onEdit = { entryToEdit = entry },
                            onDelete = { entryToDelete = entry }
                        )
                    }
                }
            }
        }
    }

    // New / Edit Dialog
    if (isCreatingNew || entryToEdit != null) {
        JournalEditorDialog(
            initialEntry = entryToEdit,
            lang = lang,
            onDismiss = {
                isCreatingNew = false
                entryToEdit = null
            },
            onSave = { updatedEntry ->
                viewModel.saveJournal(updatedEntry)
                isCreatingNew = false
                entryToEdit = null
            }
        )
    }

    // Delete Confirmation Dialog
    if (entryToDelete != null) {
        val target = entryToDelete!!
        AlertDialog(
            onDismissRequest = { entryToDelete = null },
            icon = { Icon(Icons.Default.Delete, contentDescription = null, tint = AppTheme.colors.error) },
            title = { Text(Strings.get("confirm_delete", lang)) },
            text = { Text("Delete '${target.title}' permanently?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteJournal(target.id)
                        entryToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.error)
                ) {
                    Text(Strings.get("delete", lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { entryToDelete = null }) {
                    Text(Strings.get("cancel", lang))
                }
            }
        )
    }
}

@Composable
fun JournalEntryCard(
    entry: JournalEntryEntity,
    lang: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("journal_card_${entry.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AppTheme.colors.accentContainer
                ) {
                    Text(
                        text = entry.section,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AppTheme.colors.accentOnContainer,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = entry.date,
                        style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                    )
                    IconButton(onClick = onEdit, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = AppTheme.colors.iconSecondary, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppTheme.colors.iconSecondary, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.textPrimary
                )
            )

            if (entry.content.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = entry.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppTheme.colors.textSecondary,
                        lineHeight = 20.sp
                    )
                )
            }

            // What I learned
            if (entry.whatLearned.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = AppTheme.colors.success,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${Strings.get("what_learned", lang)}: ${entry.whatLearned}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppTheme.colors.textPrimary,
                            lineHeight = 18.sp
                        )
                    )
                }
            }

            // Mistakes made
            if (entry.mistakesMade.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = AppTheme.colors.error,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${Strings.get("mistakes_made", lang)}: ${entry.mistakesMade}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppTheme.colors.textPrimary,
                            lineHeight = 18.sp
                        )
                    )
                }
            }

            // Stats row (duration, questions)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (entry.durationMinutes > 0) {
                    Text(
                        text = "⏱ ${entry.durationMinutes} ${Strings.get("min", lang)}",
                        style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                    )
                }
                if (entry.questionsAttempted > 0) {
                    Text(
                        text = "🎯 ${entry.questionsCorrect}/${entry.questionsAttempted} correct",
                        style = MaterialTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEditorDialog(
    initialEntry: JournalEntryEntity?,
    lang: String,
    onDismiss: () -> Unit,
    onSave: (JournalEntryEntity) -> Unit
) {
    val todayStr = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }

    var title by remember { mutableStateOf(initialEntry?.title ?: "") }
    var content by remember { mutableStateOf(initialEntry?.content ?: "") }
    var section by remember { mutableStateOf(initialEntry?.section ?: "Reading") }
    var durationText by remember { mutableStateOf(initialEntry?.durationMinutes?.takeIf { it > 0 }?.toString() ?: "30") }
    var questionsAttemptedText by remember { mutableStateOf(initialEntry?.questionsAttempted?.takeIf { it > 0 }?.toString() ?: "") }
    var questionsCorrectText by remember { mutableStateOf(initialEntry?.questionsCorrect?.takeIf { it > 0 }?.toString() ?: "") }
    var whatLearned by remember { mutableStateOf(initialEntry?.whatLearned ?: "") }
    var mistakesMade by remember { mutableStateOf(initialEntry?.mistakesMade ?: "") }
    var improvementPlan by remember { mutableStateOf(initialEntry?.improvementPlan ?: "") }

    val sections = listOf("Reading", "Writing Task 2", "Listening", "Speaking", "Writing Task 1", "Vocabulary", "Grammar", "Mock Test", "General")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
                .padding(8.dp)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (initialEntry == null) Strings.get("new_entry", lang) else Strings.get("edit_entry", lang),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        // Section chips
                        Text("IELTS Section", style = MaterialTheme.typography.labelMedium.copy(color = AppTheme.colors.textSecondary))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        ) {
                            items(sections) { sec ->
                                FilterChip(
                                    selected = section == sec,
                                    onClick = { section = sec },
                                    label = { Text(sec, fontSize = 11.sp) }
                                )
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title / Topic") },
                            placeholder = { Text("e.g. Cambridge 17 Passage 2 - Tomato Domestication") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text(Strings.get("what_studied", lang)) },
                            placeholder = { Text("Summary of the lesson, strategies applied...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = durationText,
                                onValueChange = { durationText = it.filter { c -> c.isDigit() } },
                                label = { Text(Strings.get("duration_mins", lang)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = questionsAttemptedText,
                                onValueChange = { questionsAttemptedText = it.filter { c -> c.isDigit() } },
                                label = { Text(Strings.get("questions_attempted", lang)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = questionsCorrectText,
                                onValueChange = { questionsCorrectText = it.filter { c -> c.isDigit() } },
                                label = { Text(Strings.get("questions_correct", lang)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = whatLearned,
                            onValueChange = { whatLearned = it },
                            label = { Text(Strings.get("what_learned", lang)) },
                            placeholder = { Text("Key insights, new vocabulary, technique learned...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = mistakesMade,
                            onValueChange = { mistakesMade = it },
                            label = { Text(Strings.get("mistakes_made", lang)) },
                            placeholder = { Text("Why I lost marks, distractor trap, word-grabbing...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = improvementPlan,
                            onValueChange = { improvementPlan = it },
                            label = { Text(Strings.get("improvement_plan", lang)) },
                            placeholder = { Text("Strategy for next practice...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(Strings.get("cancel", lang))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.isBlank()) {
                                title = "$section Study Session"
                            }
                            val entry = (initialEntry ?: JournalEntryEntity(
                                date = todayStr,
                                title = title,
                                content = content,
                                section = section
                            )).copy(
                                title = title,
                                content = content,
                                section = section,
                                durationMinutes = durationText.toIntOrNull() ?: 0,
                                questionsAttempted = questionsAttemptedText.toIntOrNull() ?: 0,
                                questionsCorrect = questionsCorrectText.toIntOrNull() ?: 0,
                                whatLearned = whatLearned,
                                mistakesMade = mistakesMade,
                                improvementPlan = improvementPlan
                            )
                            onSave(entry)
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
