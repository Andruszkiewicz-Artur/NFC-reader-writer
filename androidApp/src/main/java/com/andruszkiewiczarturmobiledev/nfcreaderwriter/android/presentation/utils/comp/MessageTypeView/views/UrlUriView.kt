package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlUriView(
    onClickAddValue: (String) -> Unit
) {
    var tagValue by remember { mutableStateOf("") }
    var isPresentedLeadingMenu by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var typeOfConnection by remember { mutableStateOf("https://") }
    val listOfTypes = listOf(
        "http://",
        "https://",
        "ftp://",
        "sftp://",
        "file://",
        "rtsp://",
        "telnet://",
        "smb://",
        "nfs://",
        "dav://",
    )

    ScaffoldMessageMakerView(
        onClickAdd = {
            onClickAddValue(typeOfConnection + tagValue)
            tagValue = ""
        }
    ) {
        Column {
            TextFieldNFCCard(
                value = tagValue,
                keyboardType = KeyboardType.Uri,
                placeHolder = stringResource(id = R.string.EnteredUrlUri),
                enteredValue = { tagValue = it },
                onDone = { keyboardController?.hide() },
                leadingValue = typeOfConnection,
                onClickLeadingValue = {
                    isPresentedLeadingMenu = !isPresentedLeadingMenu
                },
                isPresentTypesLeadingValues = isPresentedLeadingMenu,
                modifier = Modifier
            )

            MenuNFC(
                isExpended = isPresentedLeadingMenu,
                listOfVales = listOfTypes,
                onClickDismiss = { isPresentedLeadingMenu = false },
                onClickValue = { type ->
                    typeOfConnection = type
                }
            )
        }
    }
}