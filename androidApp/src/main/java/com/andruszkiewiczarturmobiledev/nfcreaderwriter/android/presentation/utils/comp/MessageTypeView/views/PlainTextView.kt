package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.ScaffoldMessageMakerView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TextFieldNFCCard

@Composable
fun PlainTextView(
    onClickAddValue: (String) -> Unit
) {
    var tagValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    ScaffoldMessageMakerView(
        onClickAdd = {
            onClickAddValue(tagValue)
            tagValue = ""
        }
    ) {
        TextFieldNFCCard(
            value = tagValue,
            placeHolder = stringResource(id = R.string.EnteredNewTag),
            enteredValue = { tagValue = it },
            onDone = { keyboardController?.hide() }
        )
    }
}