package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.NFCState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp.ReadViewPresentation
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp.WritePresentation

@Composable
fun NavHostMain(
    navHostController: NavHostController,
    nfcState: NFCState?
) {

    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Screen.ReadPresentation.route
    ) {
        composable(
            route = Screen.ReadPresentation.route
        ) {
            ReadViewPresentation(state = nfcState)
        }

        composable(
            route = Screen.WritePresentation.route
        ) {
            WritePresentation()
        }
    }
}