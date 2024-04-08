package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BasicWriteRow(
    type: String,
    message: String,
    onClickRemove: () -> Unit
) {

    ListItem(
        headlineContent = {
            Text(
                text = type
            )
        },
        supportingContent = {
            Text(
                text = message
            )
        },
        trailingContent = {
            IconButton(onClick = { onClickRemove() }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        },
        shadowElevation = 8.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
    )
}