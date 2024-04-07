package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BasicEmulateRow(
    isChosen: Boolean,
    type: String,
    message: String,
    isLast: Boolean,
    onClickRemove: () -> Unit,
    onClickRow: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickRow()
            }
            .background(
                color = if(isChosen) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background
            )
    ) {
        Column {
            Text(
                text = type,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = message
            )
        }

        IconButton(onClick = { onClickRemove() }) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }

    if (!isLast) {
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
    }

}