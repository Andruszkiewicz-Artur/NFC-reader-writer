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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WritePresentation(
    onClickSendMessage: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(onClick = { onClickSendMessage(message) }) {
                Text(text = "Set Nfc Card")
            }
        }
        
    }
    
}