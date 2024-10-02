package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldNFCCard(
    value: String = "",
    placeHolder: String = "",
    enteredValue: (String) -> Unit = { },
    autoCorrect: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit = { },
    onNext: () -> Unit = { },
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
    leadingValue: String? = null,
    isPresentTypesLeadingValues: Boolean = false,
    onClickLeadingValue: () -> Unit = { }
) {
    if (leadingValue != null) {
        OutlinedTextField(
            placeholder = {
                Text(text = placeHolder)
            },
            maxLines = maxLines,
            value = value,
            onValueChange = { enteredValue(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = capitalization,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            leadingIcon = {
                TextButton(onClick = { onClickLeadingValue() }) {
                    AnimatedContent(
                        targetState = isPresentTypesLeadingValues,
                        label = ""
                    ) { isPresented ->
                        if (isPresented) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropUp,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    }
                    Text(text = leadingValue)
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                },
                onNext = {
                    onNext()
                }
            ),
            modifier = modifier
                .fillMaxWidth()
        )
    } else {
        OutlinedTextField(
            placeholder = {
                Text(text = placeHolder)
            },
            value = value,
            onValueChange = { enteredValue(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = capitalization,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                },
                onNext = {
                    onNext()
                }
            ),
            modifier = modifier
                .fillMaxWidth()
        )
    }
}