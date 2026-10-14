package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.components.AppTopBar
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.util.Strings
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SecondaryScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val prefs by viewModel.userPreferences.collectAsState()
            val currentTab by viewModel.currentTab.collectAsState()
            val secondaryScreen by viewModel.secondaryScreen.collectAsState()

            val isDarkTheme = when (prefs.themeMode) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }

            MyApplicationTheme(darkTheme = isDarkTheme) {
                // Back navigation handling
                BackHandler(enabled = secondaryScreen != SecondaryScreen.NONE || currentTab != NavTab.HOME) {
                    if (secondaryScreen != SecondaryScreen.NONE) {
                        viewModel.closeSecondaryScreen()
                    } else if (currentTab != NavTab.HOME) {
                        viewModel.setTab(NavTab.HOME)
                    }
                }

                val currentTitle = when (secondaryScreen) {
                    SecondaryScreen.VOCABULARY -> Strings.get("vocab_notebook", prefs.language)
                    SecondaryScreen.PRACTICE_TESTS -> Strings.get("practice_tests", prefs.language)
                    SecondaryScreen.ERROR_LOG -> Strings.get("error_log", prefs.language)
                    SecondaryScreen.ASSESSMENTS -> Strings.get("weekly_assessments", prefs.language)
                    SecondaryScreen.RESOURCES -> Strings.get("resources", prefs.language)
                    SecondaryScreen.SETTINGS -> Strings.get("settings_title", prefs.language)
                    SecondaryScreen.ONBOARDING, SecondaryScreen.NONE -> {
                        when (currentTab) {
                            NavTab.HOME -> Strings.get("app_name", prefs.language)
                            NavTab.PLAN -> Strings.get("study_plan_title", prefs.language)
                            NavTab.FOCUS -> Strings.get("nav_focus", prefs.language)
                            NavTab.JOURNAL -> Strings.get("nav_journal", prefs.language)
                            NavTab.PROGRESS -> Strings.get("nav_progress", prefs.language)
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing,
                    topBar = {
                        AppTopBar(
                            title = currentTitle,
                            lang = prefs.language,
                            showBackButton = secondaryScreen != SecondaryScreen.NONE,
                            onBack = { viewModel.closeSecondaryScreen() },
                            onOpenResources = { viewModel.openSecondaryScreen(SecondaryScreen.RESOURCES) },
                            onOpenSettings = { viewModel.openSecondaryScreen(SecondaryScreen.SETTINGS) },
                            onToggleLanguage = {
                                val next = if (prefs.language == "en") "bn" else "en"
                                viewModel.setLanguage(next)
                            }
                        )
                    },
                    bottomBar = {
                        if (secondaryScreen == SecondaryScreen.NONE) {
                            BottomNavBar(
                                selectedTab = currentTab,
                                onTabSelected = { viewModel.setTab(it) },
                                lang = prefs.language
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    ) {
                        when (secondaryScreen) {
                            SecondaryScreen.VOCABULARY -> {
                                VocabularyScreen(
                                    viewModel = viewModel,
                                    lang = prefs.language,
                                    onBack = { viewModel.closeSecondaryScreen() }
                                )
                            }
                            SecondaryScreen.PRACTICE_TESTS -> {
                                PracticeTestsScreen(
                                    viewModel = viewModel,
                                    lang = prefs.language,
                                    onBack = { viewModel.closeSecondaryScreen() }
                                )
                            }
                            SecondaryScreen.ERROR_LOG -> {
                                ErrorLogScreen(
                                    viewModel = viewModel,
                                    lang = prefs.language,
                                    onBack = { viewModel.closeSecondaryScreen() }
                                )
                            }
                            SecondaryScreen.ASSESSMENTS -> {
                                AssessmentsScreen(
                                    viewModel = viewModel,
                                    lang = prefs.language,
                                    onBack = { viewModel.closeSecondaryScreen() }
                                )
                            }
                            SecondaryScreen.RESOURCES -> {
                                ResourcesScreen(
                                    lang = prefs.language,
                                    onBack = { viewModel.closeSecondaryScreen() }
                                )
                            }
                            SecondaryScreen.SETTINGS -> {
                                SettingsScreen(
                                    viewModel = viewModel,
                                    lang = prefs.language,
                                    onBack = { viewModel.closeSecondaryScreen() }
                                )
                            }
                            SecondaryScreen.ONBOARDING, SecondaryScreen.NONE -> {
                                when (currentTab) {
                                    NavTab.HOME -> {
                                        HomeScreen(
                                            viewModel = viewModel,
                                            lang = prefs.language,
                                            onNavigateToTab = { viewModel.setTab(it) },
                                            onOpenSecondary = { viewModel.openSecondaryScreen(it) },
                                            onSelectDay = { dayNum ->
                                                viewModel.selectDay(dayNum)
                                                viewModel.setTab(NavTab.PLAN)
                                            }
                                        )
                                    }
                                    NavTab.PLAN -> {
                                        StudyPlanScreen(
                                            viewModel = viewModel,
                                            lang = prefs.language
                                        )
                                    }
                                    NavTab.FOCUS -> {
                                        FocusTimerScreen(
                                            viewModel = viewModel,
                                            lang = prefs.language
                                        )
                                    }
                                    NavTab.JOURNAL -> {
                                        JournalScreen(
                                            viewModel = viewModel,
                                            lang = prefs.language
                                        )
                                    }
                                    NavTab.PROGRESS -> {
                                        ProgressScreen(
                                            viewModel = viewModel,
                                            lang = prefs.language
                                        )
                                    }
                                }
                            }
                        }

                        // First-time onboarding dialog if not configured yet
                        if (!prefs.isOnboarded) {
                            OnboardingDialog(
                                currentPrefs = prefs,
                                onComplete = { updated ->
                                    viewModel.updatePreferences(updated)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
