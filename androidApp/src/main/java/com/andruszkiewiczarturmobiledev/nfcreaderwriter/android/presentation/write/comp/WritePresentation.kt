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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainViewModel

@Composable
fun WritePresentation(
    viewModel: MainViewModel
) {
    val state = viewModel.state.collectAsState().value
    
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
                value = state.writeMessage,
                onValueChange = {
                    viewModel.onEvent(MainEvent.EnteredWriteMessage(it))
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(onClick = {
                viewModel.onEvent(MainEvent.OnClickSetAlertDialog(Type.Write))
            }) {
                Text(text = "Set a value for the NFC card")
            }
        }
    }
    
}