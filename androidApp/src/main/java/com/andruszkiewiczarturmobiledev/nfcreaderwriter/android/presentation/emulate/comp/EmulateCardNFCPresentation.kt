package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.ScaffoldNFC

@Composable
fun EmulateCardNFCPresentation(
    viewModel: MainViewModel
) {
    val state = viewModel.state.collectAsState().value

    ScaffoldNFC(
        showFloatingButton = state.emulationChosen != null,
        onClickAddButton = { viewModel.onEvent(MainEvent.AddEmulateMessage) },
        textFieldValue = state.emulateState.message,
        textFloatingButton = "Emulate NFC Card",
        iconFloatingButton = Icons.Rounded.CreditCard,
        onClickFloatingButton = {
            viewModel.onEvent(MainEvent.OnClickSetAlertDialog(Type.Emulate))
            viewModel.onEvent(MainEvent.EmulateNFCCard)
        },
        textFieldPlaceholder = "Entered new tag...",
        textFieldChangeValue = { viewModel.onEvent(MainEvent.EnteredEmulateCardMessage(it)) },
        messages = state.emulateState.messages,
        rowView = { message ->
            BasicEmulateRow(
                isChosen = message == state.emulationChosen,
                type = message.first,
                message = message.second,
                isLast = state.emulateState.messages.last() == message,
                onClickRow = {
                    viewModel.onEvent(MainEvent.ChooseEmulationMessage(message))
                },
                onClickRemove = { viewModel.onEvent(MainEvent.ShowDeletedDialog(Triple(message.first, message.second, Type.Emulate))) }
            )
        }
    )

}