package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.ScaffoldNFC
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateUiEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainUiEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp.DeleteDialog
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.Static
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject

@Composable
fun EmulateCardNFCPresentation(
    viewModel: EmulateViewModel = koinInject(),
    emulateValue: (String) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                is EmulateUiEvent.EmulateValue -> {
                    emulateValue(event.message)
                }
            }
        }
    }

    ScaffoldNFC(
        showFloatingButton = state.emulateChoose != null,
        onClickAddButton = { viewModel.onEvent(EmulateEvent.AddEmulateValue) },
        textFloatingButton = stringResource(id = string.EmulateNFCCard),
        iconFloatingButton = Icons.Rounded.CreditCard,
        onClickFloatingButton = {
            viewModel.onEvent(EmulateEvent.EmulateNFCCard)
        },
        textFieldPlaceholder = stringResource(id = string.EnteredNewTag),
        textFieldChangeValue = { viewModel.onEvent(EmulateEvent.EnteredEmulateValue(it)) },
        messages = state.listOfValues,
        tagState = state.currentValue,
        onChangeTypeValue = { typeValue ->
            viewModel.onEvent(EmulateEvent.SetTypeData(typeValue))
        },
        rowView = { message ->
            BasicEmulateRow(
                isChosen = message == state.emulateChoose,
                type = stringResource(id = Static.listOfTypes[message.type]?.name ?: 0),
                message = message.message,
                isLast = state.listOfValues.last() == message,
                onClickRow = {
                    viewModel.onEvent(EmulateEvent.FocusOnEmulatedValue(message))
                },
                onClickRemove = { viewModel.onEvent(EmulateEvent.ShowDeletedDialog(message)) }
            )
        }
    )

    DeleteDialog(
        isPresented = state.deleteValue != null,
        onClickDismissButton = { viewModel.onEvent(EmulateEvent.ShowDeletedDialog(null)) },
        onClickConfirmButton = {
            viewModel.onEvent(EmulateEvent.RemoveEmulateValue)
        }
    )
}