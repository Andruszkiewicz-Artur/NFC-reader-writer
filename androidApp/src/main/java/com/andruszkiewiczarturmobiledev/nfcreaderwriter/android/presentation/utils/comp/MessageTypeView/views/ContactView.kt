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
fun ContactView(
    onClickAddValue: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ScaffoldMessageMakerView(
        onClickAdd = {
            onClickAddValue("Name: $name\nCompany: $company\nAddress: $address\nPhone number: $phoneNumber\nEmail: $email\nWebsite: $website")
            name = ""
            company = ""
            address = ""
            phoneNumber = ""
            email = ""
            website = ""
        }
    ) {
        TextFieldNFCCard(
            value = name,
            placeHolder = stringResource(id = R.string.NameOfContactPlaceholder),
            enteredValue = { name = it },
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = company,
            placeHolder = stringResource(id = R.string.CompanyPlaceholder),
            enteredValue = { company = it },
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = address,
            placeHolder = stringResource(id = R.string.AddressPlaceholder),
            enteredValue = { address = it },
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = phoneNumber,
            placeHolder = stringResource(id = R.string.PhoneNumberPlaceholder),
            enteredValue = { phoneNumber = it },
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = email,
            placeHolder = stringResource(id = R.string.EmailAddressPlaceholder),
            enteredValue = { email = it },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldNFCCard(
            value = website,
            placeHolder = stringResource(id = R.string.WebsitePlaceholder),
            enteredValue = { website = it },
            keyboardType = KeyboardType.Uri,
            onDone = { keyboardController?.hide() }
        )
    }
}