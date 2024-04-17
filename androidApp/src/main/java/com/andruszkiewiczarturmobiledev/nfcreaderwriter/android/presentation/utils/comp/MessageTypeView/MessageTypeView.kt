package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.PlainTextView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.UrlUriView

@Composable
fun MessageTypeView(
    typeData: TypeData,
    onClickAddButton: (String) -> Unit
) {
    AnimatedContent(
        targetState = typeData,
        label = ""
    ) { innerTypeData ->
        when(innerTypeData) {
            TypeData.PlainText -> { PlainTextView(onClickAddValue = onClickAddButton) }
            TypeData.URLURI -> { UrlUriView(onClickAddValue = onClickAddButton) }
            TypeData.OwnURLURI -> {

            }
            TypeData.Search -> {

            }
            TypeData.SocialNetwork -> {

            }
            TypeData.Video -> {

            }
            TypeData.File -> {

            }
            TypeData.Application -> {

            }
            TypeData.Email -> {

            }
            TypeData.Contact -> {

            }
            TypeData.PhoneNumber -> {

            }
            TypeData.SMS -> {

            }
            TypeData.Location -> {

            }
            TypeData.OwnLocation -> {

            }
            TypeData.Address -> {

            }
            TypeData.WiFi -> {

            }
            TypeData.Data -> {

            }
        }
    }
}