package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.ScaffoldNFC
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TextFieldNFCCard
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp.DeleteDialog
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.WriteEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.WriteViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.Static
import org.koin.compose.koinInject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WritePresentation(
    viewModel: WriteViewModel = koinInject(),
    writeNFCCard: (List<TagValue>) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    ScaffoldNFC(
        showFloatingButton = state.listOfValues.isNotEmpty(),
        onClickAddButton = { viewModel.onEvent(WriteEvent.AddWriteValue) },
        textFloatingButton = stringResource(id = string.SaveOnNFCCard),
        iconFloatingButton = Icons.Rounded.Edit,
        onClickFloatingButton = {
            if (state.listOfValues.isNotEmpty())
                writeNFCCard(state.listOfValues)
        },
        tagState = state.currentValue,
        textFieldPlaceholder = stringResource(id = string.EnteredNewTag),
        textFieldChangeValue = { viewModel.onEvent(WriteEvent.EnteredWriteValue(it)) },
        messages = state.listOfValues,
        onChangeTypeValue = { typeValue ->
            viewModel.onEvent(WriteEvent.SetTypeData(typeValue))
        },
        rowView = { message ->
            BasicWriteRow(
                type = stringResource(id = Static.listOfTypes[message.type]?.name ?: 0),
                message = message.message,
                onClickRemove = { viewModel.onEvent(WriteEvent.ShowDeletedDialog(message)) }
            )
        }
    )

    DeleteDialog(
        isPresented = state.deleteValue != null,
        onClickDismissButton = { viewModel.onEvent(WriteEvent.ShowDeletedDialog(null)) },
        onClickConfirmButton = { viewModel.onEvent(WriteEvent.RemoveWriteValue) }
    )
    
}