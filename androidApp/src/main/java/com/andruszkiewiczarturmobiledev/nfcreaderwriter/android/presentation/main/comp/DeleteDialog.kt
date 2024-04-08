package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type

@Composable
fun DeleteDialog(
    message: Triple<String, String, Type>?,
    onClickDismissButton: () -> Unit,
    onClickConfirmButton: () -> Unit
) {
    AnimatedVisibility(visible = message != null) {
        AlertDialog(
            onDismissRequest = { onClickDismissButton() },
            confirmButton = {
                TextButton(onClick = {
                    onClickConfirmButton()
                }) {
                    Text(
                        text = "Confirm"
                    )
                }
            },
            title = {
                Text(
                    text = "Delete value"
                )
            },
            text = {
                Column {
                    Text(
                        text = "Are you sure you wanna delete this value?"
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onClickDismissButton()
                }) {
                    Text(
                        text = "Dismiss"
                    )
                }
            }
        )
    }
}