package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.ContactView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.EmailView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.OwnUrlUriView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.PhoneNumberView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.PlainTextView
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.MessageTypeView.views.SmsView
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
            TypeData.OwnURLURI -> { OwnUrlUriView(onClickAddValue = onClickAddButton) }
            TypeData.Email -> { EmailView(onClickAddValue = onClickAddButton) }
            TypeData.Contact -> { ContactView(onClickAddValue = onClickAddButton) }
            TypeData.PhoneNumber -> { PhoneNumberView(onClickAddValue = onClickAddButton) }
            TypeData.SMS -> { SmsView(onClickAddValue = onClickAddButton) }
            TypeData.Location -> {  }
            TypeData.OwnLocation -> {  }
            TypeData.Address -> {  }
            TypeData.WiFi -> {  }
            TypeData.Data -> {  }
        }
    }
}