package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
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
                OutlinedButton(onClick = {
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
                    Text(
                        text = "\n" +
                                "${message?.first}\n" +
                                "${message?.second}",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(onClick = {
                    onClickDismissButton()
                }) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }
}