package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp.EmulateCardNFCPresentation
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCEmulateState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCReadState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCWriteState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp.ReadViewPresentation
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp.WritePresentation

@Composable
fun NavHostMain(
    navHostController: NavHostController,
    nfcStateRead: NFCReadState?,

    nfcWriteState: NFCWriteState,
    enteredMessage: (String) -> Unit,
    onClickSendMessage: () -> Unit,
    onClickDismissAlertDialog: () -> Unit,

    nfcEmulateState: NFCEmulateState,
    enteredTag: (String) -> Unit,
    onClickEmulateCard: () -> Unit,
    onClickDismissEmulateAlertDialog: () -> Unit
) {

    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Screen.ReadPresentation.route
    ) {
        composable(
            route = Screen.ReadPresentation.route
        ) {
            ReadViewPresentation(state = nfcStateRead)
        }

        composable(
            route = Screen.WritePresentation.route
        ) {
            WritePresentation(
                nfcWriteState = nfcWriteState,
                enteredMessage = enteredMessage,
                onClickSendMessage = onClickSendMessage,
                onClickDismissAlertDialog = onClickDismissAlertDialog
            )
        }

        composable(
            route = Screen.EmulatePresentation.route
        ) {
            EmulateCardNFCPresentation(
                nfcEmulateState = nfcEmulateState,
                enteredTag = enteredTag,
                onClickEmulateCard = onClickEmulateCard,
                onClickDismissEmulateAlertDialog = onClickDismissEmulateAlertDialog
            )
        }
    }
}