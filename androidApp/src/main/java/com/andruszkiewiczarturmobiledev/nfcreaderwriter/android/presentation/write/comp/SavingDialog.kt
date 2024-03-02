package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCWriteState

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