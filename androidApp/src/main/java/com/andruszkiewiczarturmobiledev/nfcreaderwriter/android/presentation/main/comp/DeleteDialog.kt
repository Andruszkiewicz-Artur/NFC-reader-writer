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
import androidx.compose.ui.res.stringResource
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue

@Composable
fun DeleteDialog(
    isPresented: Boolean,
    onClickDismissButton: () -> Unit,
    onClickConfirmButton: () -> Unit
) {
    AnimatedVisibility(visible = isPresented) {
        AlertDialog(
            onDismissRequest = { onClickDismissButton() },
            confirmButton = {
                TextButton(onClick = {
                    onClickConfirmButton()
                }) {
                    Text(
                        text = stringResource(id = string.Confirm)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = string.DeleteValue)
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = string.SureToDelete)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onClickDismissButton()
                }) {
                    Text(
                        text = stringResource(id = string.Dismiss)
                    )
                }
            }
        )
    }
}