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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WritePresentation(
    viewModel: MainViewModel
) {
    val state = viewModel.state.collectAsState().value

    ScaffoldNFC(
        showFloatingButton = state.writeStateList.isNotEmpty(),
        onClickAddButton = { viewModel.onEvent(MainEvent.AddWriteMessage) },
        textFloatingButton = stringResource(id = string.SaveOnNFCCard),
        iconFloatingButton = Icons.Rounded.Edit,
        onClickFloatingButton = { viewModel.onEvent(MainEvent.OnClickSetAlertDialog(Type.Write)) },
        tagState = state.writeState,
        textFieldPlaceholder = stringResource(id = string.EnteredNewTag),
        textFieldChangeValue = { viewModel.onEvent(MainEvent.EnteredWriteMessage(it)) },
        messages = state.writeStateList,
        types = state.typesOfData,
        onChangeTypeValue = { typeValue ->
            viewModel.onEvent(MainEvent.SetDataType(typeValue.type, typeValue.name, Type.Write))
        },
        rowView = { message ->
            BasicWriteRow(
                type = stringResource(id = message.typeValue),
                message = message.message,
                onClickRemove = { viewModel.onEvent(MainEvent.ShowDeletedDialog(message, Type.Write)) }
            )
        }
    )
    
}