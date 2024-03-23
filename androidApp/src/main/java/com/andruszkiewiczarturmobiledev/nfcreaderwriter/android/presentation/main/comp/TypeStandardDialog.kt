package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type

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
                    onClickDismissDialog()
                }) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }
}