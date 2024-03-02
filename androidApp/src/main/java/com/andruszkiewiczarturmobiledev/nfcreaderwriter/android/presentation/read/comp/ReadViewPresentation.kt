package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RestorePage
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCReadState

@Composable
fun ReadViewPresentation(
    state: NFCReadState?
) {
    if (state == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Don`t read card yet!")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                RowViewInRead(
                    imageVector = Icons.Outlined.Sell,
                    title = "Tag kind",
                    content = state.tagKind
                )

                RowViewInRead(
                    imageVector = Icons.Outlined.Key,
                    title = "SN",
                    content = state.sn
                )

                RowViewInRead(
                    imageVector = Icons.Outlined.VpnKey,
                    title = "System Code",
                    content = state.systemCode
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Build,
                    title = "Techs",
                    content = state.techs
                )

                RowViewInRead(
                    letter = 'A',
                    title = "ATQA",
                    content = state.atqa
                )

                RowViewInRead(
                    letter = 'S',
                    title = "SAK",
                    content = state.sak
                )

                RowViewInRead(
                    letter = 'D',
                    title = "DSF Id",
                    content = state.dsfId
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Storage,
                    title = "Storage",
                    content = state.storage
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Folder,
                    title = "Max size of Storage",
                    content = state.maxSizeStorage
                )

                RowViewInRead(
                    imageVector = Icons.Outlined.Info,
                    title = "Format data",
                    content = state.dataFormat
                )

                RowViewInRead(
                    imageVector = Icons.Filled.RestorePage,
                    title = "Is writable",
                    content = state.isWritable?.toString()
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Lock,
                    title = "Can set only to read",
                    content = state.canSetOnlyToRead?.toString()
                )

                RowViewInRead(
                    imageVector = Icons.Default.ListAlt,
                    title = state.typeOfMessage ?: "Message",
                    content = state.message,
                    isLast = true
                )
            }
        }
    }
}