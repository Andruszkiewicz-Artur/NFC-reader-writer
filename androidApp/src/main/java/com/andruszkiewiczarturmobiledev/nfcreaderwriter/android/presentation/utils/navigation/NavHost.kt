package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp.EmulateCardNFCPresentation
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp.ReadViewPresentation
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp.WritePresentation

@Composable
fun NavHostMain(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Screen.ReadPresentation.route
    ) {
        composable(
            route = Screen.ReadPresentation.route
        ) {
            ReadViewPresentation(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.WritePresentation.route
        ) {
            WritePresentation(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.EmulatePresentation.route
        ) {
            EmulateCardNFCPresentation(
                viewModel = viewModel
            )
        }
    }
}