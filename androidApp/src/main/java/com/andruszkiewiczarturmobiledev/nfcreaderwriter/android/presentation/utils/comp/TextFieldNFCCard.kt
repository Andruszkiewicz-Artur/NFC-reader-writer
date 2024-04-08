package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextFieldNFCCard(
    value: String = "",
    placeHolder: String = "",
    enteredValue: (String) -> Unit = { },
    onClickPlus: () -> Unit = { },
    autoCorrect: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        placeholder = {
            Text(text = placeHolder)
        },
        value = value,
        onValueChange = { enteredValue(it) },
        trailingIcon = {
            IconButton(onClick = { onClickPlus() }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add"
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = autoCorrect,
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onClickPlus()
                keyboardController?.hide()
            }
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}