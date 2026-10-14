package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.ui.util.Strings
import com.example.ui.viewmodel.NavTab

@Composable
fun BottomNavBar(
    selectedTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    lang: String
) {
    NavigationBar(
        modifier = Modifier.testTag("bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = NavigationBarDefaults.Elevation
    ) {
        NavigationBarItem(
            selected = selectedTab == NavTab.HOME,
            onClick = { onTabSelected(NavTab.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = Strings.get("nav_home", lang)) },
            label = { Text(text = Strings.get("nav_home", lang), maxLines = 1, softWrap = false) },
            alwaysShowLabel = true,
            modifier = Modifier.testTag("nav_item_home")
        )

        NavigationBarItem(
            selected = selectedTab == NavTab.PLAN,
            onClick = { onTabSelected(NavTab.PLAN) },
            icon = { Icon(Icons.Default.Assignment, contentDescription = Strings.get("nav_plan", lang)) },
            label = { Text(text = Strings.get("nav_plan", lang), maxLines = 1, softWrap = false) },
            alwaysShowLabel = true,
            modifier = Modifier.testTag("nav_item_plan")
        )

        NavigationBarItem(
            selected = selectedTab == NavTab.FOCUS,
            onClick = { onTabSelected(NavTab.FOCUS) },
            icon = { Icon(Icons.Default.Timer, contentDescription = Strings.get("nav_focus", lang)) },
            label = { Text(text = Strings.get("nav_focus", lang), maxLines = 1, softWrap = false) },
            alwaysShowLabel = true,
            modifier = Modifier.testTag("nav_item_focus")
        )

        NavigationBarItem(
            selected = selectedTab == NavTab.JOURNAL,
            onClick = { onTabSelected(NavTab.JOURNAL) },
            icon = { Icon(Icons.Default.EditNote, contentDescription = Strings.get("nav_journal", lang)) },
            label = { Text(text = Strings.get("nav_journal", lang), maxLines = 1, softWrap = false) },
            alwaysShowLabel = true,
            modifier = Modifier.testTag("nav_item_journal")
        )

        NavigationBarItem(
            selected = selectedTab == NavTab.PROGRESS,
            onClick = { onTabSelected(NavTab.PROGRESS) },
            icon = { Icon(Icons.Default.BarChart, contentDescription = Strings.get("nav_progress", lang)) },
            label = { Text(text = Strings.get("nav_progress", lang), maxLines = 1, softWrap = false) },
            alwaysShowLabel = true,
            modifier = Modifier.testTag("nav_item_progress")
        )
    }
}
