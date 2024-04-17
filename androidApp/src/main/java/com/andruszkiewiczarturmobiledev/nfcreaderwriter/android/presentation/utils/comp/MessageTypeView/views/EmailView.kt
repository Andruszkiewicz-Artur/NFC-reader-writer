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
fun EmailView(
    onClickAddValue: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ScaffoldMessageMakerView(
        onClickAdd = {
            onClickAddValue("Email: $email\nSubject: $subject\nContent: $content")
            email = ""
            subject = ""
            content = ""
        }
    ) {
        TextFieldNFCCard(
            value = email,
            placeHolder = stringResource(id = R.string.To),
            enteredValue = { email = it },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = subject,
            placeHolder = stringResource(id = R.string.Subject),
            enteredValue = { subject = it },
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = content,
            placeHolder = stringResource(id = R.string.Content),
            enteredValue = { content = it },
            onDone = { keyboardController?.hide() },
            maxLines = 10
        )
    }
}