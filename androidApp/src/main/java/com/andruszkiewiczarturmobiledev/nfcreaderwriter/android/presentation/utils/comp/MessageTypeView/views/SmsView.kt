package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.ScaffoldMessageMakerView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TextFieldNFCCard

@Composable
fun SmsView(
    onClickAddValue: (String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var sms by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ScaffoldMessageMakerView(
        onClickAdd = {
            onClickAddValue("Phone number: $phoneNumber\nSms: $sms")
            phoneNumber = ""
            sms = ""
        }
    ) {
        TextFieldNFCCard(
            value = phoneNumber,
            keyboardType = KeyboardType.Phone,
            placeHolder = stringResource(id = R.string.PhoneNumberPlaceholder),
            enteredValue = { phoneNumber = it },
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = sms,
            maxLines = 10,
            placeHolder = stringResource(id = R.string.SMSPlaceholder),
            enteredValue = { sms = it },
            onDone = { keyboardController?.hide() }
        )
    }
}