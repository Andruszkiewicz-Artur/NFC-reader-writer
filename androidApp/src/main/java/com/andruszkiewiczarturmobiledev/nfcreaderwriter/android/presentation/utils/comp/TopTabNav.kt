package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation.Screen

@Composable
fun TopTabNav(
    navHostController: NavHostController
) {
    val screens = listOf(
        Screen.ReadPresentation,
        Screen.WritePresentation
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isBottomBar = screens.any { it.route == currentDestination }

    if (isBottomBar) {
        TabRow(selectedTabIndex = screens.indexOfFirst { it.route == currentDestination }) {
            screens.forEachIndexed { index, screen ->
                 Tab(
                     selected = screen.route == currentDestination,
                     onClick = {
                         if (currentDestination != screen.route) {
                             navHostController.navigate(screen.route)
                         }
                     },
                     text = {
                         Text(text  = screen.name ?: "")
                     }
                 )
            }
        }
    }

}