package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCWriteState

@Composable
fun WritePresentation(
    nfcWriteState: NFCWriteState,
    enteredMessage: (String) -> Unit,
    onClickSendMessage: () -> Unit,
    onClickDismissAlertDialog: () -> Unit
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
                value = nfcWriteState.message,
                onValueChange = {
                    enteredMessage(it)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(onClick = {
                onClickSendMessage()
            }) {
                Text(text = "Set a value for the NFC card")
            }
        }

        SavingDialog(
            isDialog = nfcWriteState.isDialog,
            onClickDismissAlertDialog = {
                onClickDismissAlertDialog()
            }
        )
    }
    
}