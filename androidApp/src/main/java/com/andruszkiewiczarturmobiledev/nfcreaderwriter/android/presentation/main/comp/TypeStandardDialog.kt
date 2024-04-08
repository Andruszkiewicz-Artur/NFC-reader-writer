package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*

@Composable
fun TypeSavingDialog(
    type: Type?,
    onClickDismissDialog: () -> Unit
) {

    AnimatedVisibility(visible = type != null) {
        AlertDialog(
            onDismissRequest = { onClickDismissDialog() },
            confirmButton = {},
            title = {
                Text(
                    text = stringResource(id = string.WriteData)
                )
            },
            text = {
                Text(
                    text = if (type == Type.Write) stringResource(id = string.BringTheNFCCArd) else stringResource(id = string.BringTheNFCReader)
                )
            },
            dismissButton = {
                Button(onClick = {
                    onClickDismissDialog()
                }) {
                    Text(
                        text = stringResource(id = string.Dismiss)
                    )
                }
            }
        )
    }
}