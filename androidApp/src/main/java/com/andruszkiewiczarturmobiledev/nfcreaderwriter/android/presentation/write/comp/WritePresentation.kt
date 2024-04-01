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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WritePresentation(
    viewModel: MainViewModel
) {
    val state = viewModel.state.collectAsState().value
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(visible = state.writeMessages.isNotEmpty()) {
                Button(onClick = {
                    viewModel.onEvent(MainEvent.OnClickSetAlertDialog(Type.Write))
                }) {
                    Text(text = "Set a value for the NFC card")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                item {
                    Row {
                        OutlinedTextField(
                            value = state.writeMessage,
                            onValueChange = {
                                viewModel.onEvent(MainEvent.EnteredWriteMessage(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        )

                        Spacer(modifier = Modifier.padding(16.dp))

                        IconButton(onClick = { viewModel.onEvent(MainEvent.AddWriteMessage) }) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(state.writeMessages) { message ->
                    BasicWriteRow(
                        type = message.first,
                        message = message.second,
                        isLast = state.writeMessages.last() == message,
                        onClickRemove = { viewModel.onEvent(MainEvent.RemoveWriteMessage(message)) }
                    )
                }
            }
        }
    }
    
}