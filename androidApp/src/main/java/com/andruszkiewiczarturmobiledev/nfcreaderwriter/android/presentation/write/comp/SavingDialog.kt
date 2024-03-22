package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SavingDialog(
    isDialog: Boolean,
    onClickDismissAlertDialog: () -> Unit
) {

    AnimatedVisibility(visible = isDialog) {
        AlertDialog(
            onDismissRequest = { onClickDismissAlertDialog() },
            confirmButton = {},
            title = {
                Text(
                    text = "Writing Data"
                )
            },
            text = {
                Text(
                    text = "Bring the NFC card closer to your phone"
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