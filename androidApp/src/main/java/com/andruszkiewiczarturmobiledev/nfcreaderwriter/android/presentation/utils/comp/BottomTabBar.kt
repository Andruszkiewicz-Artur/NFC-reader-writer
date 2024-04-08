package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation.Screen

@Composable
fun BottomTabBar(
    navHostController: NavHostController
) {

    val screens = listOf(
        Screen.ReadPresentation,
        Screen.WritePresentation,
        Screen.EmulatePresentation
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isBottomBar = screens.any { it.route == currentDestination }

    if (isBottomBar) {
//        TabRow(selectedTabIndex = screens.indexOfFirst { it.route == currentDestination }) {
//            screens.forEachIndexed { index, screen ->
//                Tab(
//                    selected = screen.route == currentDestination,
//                    onClick = {
//                        if (currentDestination != screen.route) {
//                            navHostController.navigate(screen.route)
//                        }
//                    },
//                    text = {
//                        Text(text  = screen.name ?: "")
//                    },
//                    icon = {
//                        if (screen.icon != null) {
//                            Icon(
//                                imageVector = screen.icon,
//                                contentDescription = screen.name ?: ""
//                            )
//                        }
//                    }
//                )
//            }
//        }

        NavigationBar {
            screens.forEach { screen ->
                NavigationBarItem(
                    selected = screen.route == currentDestination,
                    onClick = {
                        navHostController.navigate(screen.route)
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon ?: Icons.Outlined.Home,
                            contentDescription = screen.name ?: ""
                        )
                    },
                    label = {
                        Text(
                            text = screen.name ?: ""
                        )
                    }
                )
            }
        }
    }
}