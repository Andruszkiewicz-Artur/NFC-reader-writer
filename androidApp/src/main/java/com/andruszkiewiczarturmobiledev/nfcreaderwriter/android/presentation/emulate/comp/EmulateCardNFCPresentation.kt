package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCEmulateState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCWriteState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp.SavingDialog

@Composable
fun EmulateCardNFCPresentation(
    nfcEmulateState: NFCEmulateState,
    enteredTag: (String) -> Unit,
    onClickEmulateCard: () -> Unit,
    onClickDismissEmulateAlertDialog: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nfcEmulateState.message,
                onValueChange = {
                    enteredTag(it)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                onClickEmulateCard()
            }) {
                Text(text = "Set a value for the NFC card")
            }
        }

        SavingDialog(
            isDialog = nfcEmulateState.isDialog,
            onClickDismissAlertDialog = {
                onClickDismissEmulateAlertDialog()
            }
        )
    }

}