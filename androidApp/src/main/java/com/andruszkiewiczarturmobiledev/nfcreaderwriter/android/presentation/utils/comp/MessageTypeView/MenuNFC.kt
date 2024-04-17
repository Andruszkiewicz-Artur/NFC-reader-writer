package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuNFC(
    isExpended: Boolean,
    listOfVales: List<String>,
    onClickDismiss: () -> Unit,
    onClickValue: (String) -> Unit
) {
    DropdownMenu(
        expanded = isExpended,
        onDismissRequest = { onClickDismiss() }
    ) {
        listOfVales.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(text = item)
                },
                onClick = {
                    onClickValue(item)
                    onClickDismiss()
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
        }
    }
}