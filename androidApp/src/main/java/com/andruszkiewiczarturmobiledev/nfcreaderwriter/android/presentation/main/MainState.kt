package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.app.PendingIntent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import androidx.compose.ui.graphics.vector.ImageVector
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R

data class MainState(
    val readCardState: NFCReadState? = null,
    val writeStateList: List<TagValue> = emptyList(),
    val typeOfDialog: Type? = null,
    val isPresentedInfoDialog: Boolean = false
)

data class TagValue(
    val type: TypeData = TypeData.PlainText,
    val message: String = ""
)

data class NFCReadState(
    var tagKind: String? = null,
    var idTag: String? = null,
    var techs: String? = null,
    var sn: String? = null,
    var systemCode: String? = null,
    var dsfId: String? = null,
    var maxSizeStorage: String? = null,
    var atqa: String? = null,
    var sak: String? = null,
    var dataFormat: String? = null,
    var storage: String? = null,
    var canSetOnlyToRead: Boolean? = null,
    var isWritable: Boolean? = null,
    var messages: List<Pair<String, String>> = emptyList()
)

enum class Type {
    Write, Emulate
}

enum class TypeData {
    PlainText,
    URLURI,
    OwnURLURI,
    Search,
    SocialNetwork,
    Video,
    File,
    Application,
    Email,
    Contact,
    PhoneNumber,
    SMS,
    Location,
    OwnLocation,
    Address,
    WiFi,
    Data
}
