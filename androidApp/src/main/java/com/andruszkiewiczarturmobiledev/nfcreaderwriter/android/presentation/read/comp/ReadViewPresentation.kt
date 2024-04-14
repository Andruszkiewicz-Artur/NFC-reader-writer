package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCReadState

@Composable
fun ReadViewPresentation(
    nfcReadState: NFCReadState?
) {
    if (nfcReadState == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = stringResource(id = string.DontReadCardYet))
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
                    title = stringResource(id = string.TagKind),
                    content = nfcReadState.tagKind
                )

                RowViewInRead(
                    imageVector = Icons.Outlined.Key,
                    title = "SN",
                    content = nfcReadState.sn
                )

                RowViewInRead(
                    imageVector = Icons.Outlined.VpnKey,
                    title = stringResource(id = string.SystemCode),
                    content = nfcReadState.systemCode
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Build,
                    title = stringResource(id = string.Techs),
                    content = nfcReadState.techs
                )

                RowViewInRead(
                    letter = 'A',
                    title = "ATQA",
                    content = nfcReadState.atqa
                )

                RowViewInRead(
                    letter = 'S',
                    title = "SAK",
                    content = nfcReadState.sak
                )

                RowViewInRead(
                    letter = 'D',
                    title = "DSF Id",
                    content = nfcReadState.dsfId
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Storage,
                    title = stringResource(id = string.Storage),
                    content = nfcReadState.storage
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Folder,
                    title = stringResource(id = string.MaxStorageSize),
                    content = nfcReadState.maxSizeStorage
                )

                RowViewInRead(
                    imageVector = Icons.Outlined.Info,
                    title = stringResource(id = string.FormatData),
                    content = nfcReadState.dataFormat
                )

                RowViewInRead(
                    imageVector = Icons.Filled.RestorePage,
                    title = stringResource(id = string.IsWritable),
                    content = if (nfcReadState.isWritable == true) stringResource(id = string.Yes) else if (nfcReadState.isWritable == false) stringResource(id = string.No) else null
                )

                RowViewInRead(
                    imageVector = Icons.Filled.Lock,
                    title = stringResource(id = string.CanSetReadOnly),
                    content = if (nfcReadState.canSetOnlyToRead == true) stringResource(id = string.Yes) else if (nfcReadState.canSetOnlyToRead == false) stringResource(id = string.No) else null
                )
            }

            items(nfcReadState.messages) { message ->
                RowViewInRead(
                    imageVector = Icons.Default.ListAlt,
                    title = message.first,
                    content = message.second,
                    isLast = true
                )
            }
        }
    }
}