package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.util.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen(
    lang: String,
    onBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Writing Task 2") }
    val categories = listOf("Writing Task 2", "Reading Strategies", "Speaking", "Writing Task 1", "Listening", "Official Links")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("resources_screen_container")
    ) {
        ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { cat ->
                    Tab(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        text = { Text(cat, fontSize = 12.sp) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                when (selectedCategory) {
                    "Writing Task 2" -> {
                        item {
                            ResourceCard(
                                title = "1. Opinion Essay (Agree / Disagree)",
                                content = "Prompt: 'To what extent do you agree or disagree?'\n\n" +
                                        "• Introduction (40-50 words): Paraphrase question + state clear position\n" +
                                        "• Body Paragraph 1 (70-90 words): 1st reason + explanation + specific example\n" +
                                        "• Body Paragraph 2 (70-90 words): 2nd reason + explanation + specific example\n" +
                                        "• Conclusion (30-40 words): Restate thesis + summarize main ideas\n" +
                                        "Total: 250–280 words."
                            )
                        }
                        item {
                            ResourceCard(
                                title = "2. Discussion Essay (Discuss Both Views)",
                                content = "Prompt: 'Discuss both views and give your opinion'\n\n" +
                                        "• Introduction: Paraphrase prompt + introduce both sides + state your stance\n" +
                                        "• Body Paragraph 1: First view + reasoning + example\n" +
                                        "• Body Paragraph 2: Second view + reasoning + example + why your opinion aligns\n" +
                                        "• Conclusion: Summarize both views + final clear verdict"
                            )
                        }
                        item {
                            ResourceCard(
                                title = "3. Problem-Solution Essay",
                                content = "Prompt: 'What are the causes and what solutions can be proposed?'\n\n" +
                                        "• Introduction: Paraphrase topic + outline that causes and remedies will be analyzed\n" +
                                        "• Body Paragraph 1: Primary causes (2 key causes) + consequences\n" +
                                        "• Body Paragraph 2: Corresponding practical solutions + explanations\n" +
                                        "• Conclusion: Summarize relationship between causes and solutions"
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Idea Generation: 5 Questions Method",
                                content = "Use Who / What / Why / Where / How to brainstorm logical points within 2 minutes.\n\n" +
                                        "Example topic: 'Gap year before university'\n" +
                                        "• Why: gain practical workplace experience, become mature, save funds\n" +
                                        "• Where: local internships, volunteer projects, low-cost travel\n" +
                                        "• How: part-time jobs, scholarship saving, remote work"
                            )
                        }
                    }

                    "Reading Strategies" -> {
                        item {
                            ResourceCard(
                                title = "True / False / Not Given Mastery",
                                content = "• Read whole statements carefully; watch out for qualifying words (some, always, mainly, all).\n" +
                                        "• Think of possible passage synonyms before scanning.\n" +
                                        "• TRUE: The passage contains the EXACT same meaning.\n" +
                                        "• FALSE: The passage explicitly CONTRADICTS the statement.\n" +
                                        "• NOT GIVEN: The passage mentions the topic, but does NOT confirm or deny the specific claim."
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Matching Headings Technique",
                                content = "• Answers are NOT in textual order. If present, do this question type FIRST!\n" +
                                        "• Read the first 1-2 sentences and final sentence of each paragraph to grasp the general topic.\n" +
                                        "• Summarize each paragraph in 1-2 words before looking at the list of headings.\n" +
                                        "• Match obvious headings first; cross off used headings carefully."
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Time Management Breakdown (60 mins)",
                                content = "• Passage 1: 16 minutes\n" +
                                        "• Passage 2: 17 minutes\n" +
                                        "• Passage 3: 18 minutes\n" +
                                        "• Answer transfer & final verification: 9 minutes\n" +
                                        "Rule: Never spend more than 90 seconds on a single question. If stuck, make an educated guess, mark it, and return later!"
                            )
                        }
                    }

                    "Speaking" -> {
                        item {
                            ResourceCard(
                                title = "The 4 Speaking Assessment Criteria",
                                content = "1. Fluency & Coherence (25%): speaking smoothly without long pauses, logical connectors.\n" +
                                        "2. Lexical Resource (25%): natural idioms, collocations, topic-specific vocabulary.\n" +
                                        "3. Grammatical Range & Accuracy (25%): complex sentences (conditionals, relative clauses).\n" +
                                        "4. Pronunciation (25%): word stress, sentence intonation, and rhythm."
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Part 2: The PREP Framework",
                                content = "During 1-min preparation, write bullet points, NOT full sentences!\n\n" +
                                        "• P (Point): State your topic directly\n" +
                                        "• R (Reason): Explain why this person, place, or object is memorable\n" +
                                        "• E (Example): Tell a specific story or vivid anecdote\n" +
                                        "• P (Point): Conclude with your feelings or long-term perspective\n\n" +
                                        "Target: Speak for the FULL 2 minutes until the examiner stops you."
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Part 3: 7 Common Question Types",
                                content = "1. Opinion: What do you think about X?\n" +
                                        "2. Evaluate: What do you think about other people's views?\n" +
                                        "3. Future: What do you predict will happen in 20 years?\n" +
                                        "4. Cause and Effect: What caused X and what impact did it have?\n" +
                                        "5. Hypothetical: Imagine an unreal scenario...\n" +
                                        "6. Compare and Contrast: How does X differ from Y?\n" +
                                        "7. Past: How was life different 50 years ago?"
                            )
                        }
                    }

                    "Writing Task 1" -> {
                        item {
                            ResourceCard(
                                title = "Academic Task 1 Four-Paragraph Blueprint",
                                content = "• Paragraph 1: Introduction (1-2 sentences) - paraphrase prompt and visual title\n" +
                                        "• Paragraph 2: Overview (2-3 sentences) - general trends, highest/lowest points (NO NUMBERS!)\n" +
                                        "• Paragraph 3: Detail Paragraph 1 (3-4 sentences) - key group 1 with data & comparisons\n" +
                                        "• Paragraph 4: Detail Paragraph 2 (3-4 sentences) - key group 2 with data & comparisons\n\n" +
                                        "Rule: An overview without numbers is strictly mandatory for Band 7+."
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Essential Trend Vocabulary",
                                content = "• Upward: soared, surged, escalated, climbed steadily, peaked at\n" +
                                        "• Downward: plummeted, plunged, dipped, slumped, declined moderately\n" +
                                        "• Stability: plateaued, leveled off, remained constant at\n" +
                                        "• Variation: fluctuated wildly, oscillated between X and Y"
                            )
                        }
                    }

                    "Listening" -> {
                        item {
                            ResourceCard(
                                title = "Shorthand Symbols & Abbreviations",
                                content = "Use standard abbreviations to write answers fast without missing audio:\n\n" +
                                        "• govt = government | approx = approximately\n" +
                                        "• max = maximum | min = minimum\n" +
                                        "• yr = year | mgmt = management\n" +
                                        "• & = and | w/ = with | w/o = without\n" +
                                        "• ↑ = increase | ↓ = decrease | → = leads to / causes"
                            )
                        }
                        item {
                            ResourceCard(
                                title = "Crucial Traps & Transfer Rules",
                                content = "• Distractor Traps: speakers often give one answer, then correct themselves ('I'll take the 5 PM train... actually, let's make it 6 PM').\n" +
                                        "• Word Count Compliance: If instructions say 'NO MORE THAN TWO WORDS', three words receive 0 marks!\n" +
                                        "• Spelling: British vs American is accepted, but misspelling names/dates loses the mark."
                            )
                        }
                    }

                    "Official Links" -> {
                        item {
                            ResourceCard(
                                title = "Curated Preparation Resources",
                                content = "• IELTS Official Portal: https://ielts.org\n" +
                                        "• British Council Free Practice: https://takeielts.britishcouncil.org\n" +
                                        "• IDP IELTS: https://ielts.idp.com\n" +
                                        "• Cambridge Official IELTS Practice Books 14–20\n" +
                                        "• IELTS Advantage Guides & VIP Materials: https://ieltsadvantage.com\n" +
                                        "• BBC Learning English (Listening & Pronunciation)\n" +
                                        "• Write & Improve with Cambridge (Automated Writing Feedback)"
                            )
                        }
                    }
                }
            }
        }
    }

@Composable
fun ResourceCard(title: String, content: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        border = BorderStroke(1.dp, AppTheme.colors.borderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.accent
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppTheme.colors.textPrimary,
                    lineHeight = 22.sp
                )
            )
        }
    }
}
