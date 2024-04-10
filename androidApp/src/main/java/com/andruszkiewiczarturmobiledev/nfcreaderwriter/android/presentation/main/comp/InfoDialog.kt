package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfoDialog(
    isPresented: Boolean,
    onClickDismiss: () -> Unit
) {
    var isShortRead by remember { mutableStateOf(true) }
    var isShortWrite by remember { mutableStateOf(true) }
    var isShortEmulate by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isPresented
    ) {
        Dialog(
            onDismissRequest = { onClickDismiss() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = string.Info)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { onClickDismiss() }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
            ) { padding ->
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .padding(bottom = 20.dp)
                            .fillMaxWidth(0.9f)
                    ) {
                        item {
                            InfoColumn(
                                title = stringResource(id = string.ReadView),
                                description = stringResource(id = string.ReadInformation),
                                listOfWorking = listOf(
                                    stringResource(id = string.ReadInformation1Step)
                                ),
                                isShort = isShortRead,
                                onClickChangeSize = {
                                    isShortRead = !isShortRead
                                }
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            InfoColumn(
                                title = stringResource(id = string.WriteView),
                                description = stringResource(id = string.WriteInformation),
                                listOfWorking = listOf(
                                    stringResource(id = string.WriteInformation1Step),
                                    stringResource(id = string.WriteInformation2Step),
                                    stringResource(id = string.WriteInformation3Step),
                                    stringResource(id = string.WriteInformation4Step),
                                    stringResource(id = string.WriteInformation5Step)
                                ),
                                isShort = isShortWrite,
                                onClickChangeSize = {
                                    isShortWrite = !isShortWrite
                                }
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            InfoColumn(
                                title = stringResource(id = string.EmulateView),
                                description = stringResource(id = string.EmulateInformation),
                                listOfWorking = listOf(
                                    stringResource(id = string.EmulateInformation1Step),
                                    stringResource(id = string.EmulateInformation2Step),
                                    stringResource(id = string.EmulateInformation3Step),
                                    stringResource(id = string.EmulateInformation4Step),
                                    stringResource(id = string.EmulateInformation5Step),
                                    stringResource(id = string.EmulateInformation6Step)
                                ),
                                isShort = isShortEmulate,
                                onClickChangeSize = {
                                    isShortEmulate = !isShortEmulate
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}