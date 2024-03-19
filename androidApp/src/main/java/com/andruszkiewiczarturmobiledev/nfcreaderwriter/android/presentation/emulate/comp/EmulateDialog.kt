package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EmulateDialog(
    isDialog: Boolean,
    onClickDismissAlertDialog: () -> Unit
) {

    AnimatedVisibility(visible = isDialog) {
        AlertDialog(
            onDismissRequest = { onClickDismissAlertDialog() },
            confirmButton = {},
            title = {
                Text(
                    text = "Emulate Card"
                )
            },
            text = {
                Text(
                    text = "Hold your phone up to the reader"
                )
            },
            dismissButton = {
                Button(onClick = {
                    onClickDismissAlertDialog()
                }) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }
}