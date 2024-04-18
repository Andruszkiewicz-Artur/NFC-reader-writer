package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.ScaffoldMessageMakerView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TextFieldNFCCard

@Composable
fun PhoneNumberView(
    onClickAddValue: (String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    ScaffoldMessageMakerView(
        onClickAdd = {
            onClickAddValue(phoneNumber)
            phoneNumber = ""
        }
    ) {
        TextFieldNFCCard(
            value = phoneNumber,
            keyboardType = KeyboardType.Phone,
            placeHolder = stringResource(id = R.string.PhoneNumberPlaceholder),
            enteredValue = { phoneNumber = it },
            onDone = { keyboardController?.hide() }
        )
    }
}