package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ContactPage
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.rounded.DatasetLinked
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.FileCopy
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.OndemandVideo
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.WifiPassword
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData

object Static {
    val listOfTypes = mapOf(
        TypeData.PlainText to TypeDataDescription(
            name = R.string.PlainText,
            icon = Icons.Rounded.Description,
            description = R.string.PlainTextDescription
        ),
        TypeData.URLURI to TypeDataDescription(
            name = R.string.URLURI,
            icon = Icons.Rounded.Link,
            description = R.string.URLURIDescription
        ),
        TypeData.OwnURLURI to TypeDataDescription(
            name = R.string.OwnURLURI,
            icon = Icons.Rounded.DatasetLinked,
            description = R.string.OwnURLURIDescription
        ),
        TypeData.Email to TypeDataDescription(
            name = R.string.Email,
            icon = Icons.Rounded.Email,
            description = R.string.EmailDescription
        ),
        TypeData.Contact to TypeDataDescription(
            name = R.string.Contact,
            icon = Icons.Outlined.ContactPage,
            description = R.string.ContactDescription
        ),
        TypeData.PhoneNumber to TypeDataDescription(
            name = R.string.PhoneNumber,
            icon = Icons.Outlined.Call,
            description = R.string.PhoneNumberDescription
        ),
        TypeData.SMS to TypeDataDescription(
            name = R.string.SMS,
            icon = Icons.Outlined.Sms,
            description = R.string.SMSDescription
        ),
        TypeData.Location to TypeDataDescription(
            name = R.string.Location,
            icon = Icons.Outlined.LocationOn,
            description = R.string.LocationDescription
        ),
        TypeData.OwnLocation to TypeDataDescription(
            name = R.string.OwnLocation,
            icon = Icons.Outlined.MyLocation,
            description = R.string.OwnLocationDescription
        ),
        TypeData.Address to TypeDataDescription(
            name = R.string.Address,
            icon = Icons.Outlined.LocalLibrary,
            description = R.string.AddressDescription
        ),
        TypeData.WiFi to TypeDataDescription(
            name = R.string.WiFi,
            icon = Icons.Rounded.WifiPassword,
            description = R.string.WiFiDescription
        ),
        TypeData.Data to TypeDataDescription(
            name = R.string.Data,
            icon = Icons.Outlined.Storage,
            description = R.string.DataDescription
        )
    )
}