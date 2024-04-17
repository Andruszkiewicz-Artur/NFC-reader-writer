package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.MenuNFC
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.ScaffoldMessageMakerView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TextFieldNFCCard

@Composable
fun OwnUrlUriView(
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
            placeHolder = stringResource(id = R.string.EnteredUrlUri),
            keyboardType = KeyboardType.Uri,
            enteredValue = { tagValue = it },
            onDone = { keyboardController?.hide() }
        )
    }
}