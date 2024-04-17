package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldMessageMakerView(
    onClickAdd: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        content()

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onClickAdd() },
            modifier = Modifier
                .fillMaxWidth(0.6f)
        ) {
            Text(text = stringResource(id = R.string.Add))
        }
    }
}