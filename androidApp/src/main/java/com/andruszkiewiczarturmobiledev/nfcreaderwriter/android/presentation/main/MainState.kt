package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter

data class MainState(
    val readCardState: NFCReadState? = null,
    val writeState: TagsValue = TagsValue(),
    val emulateState: TagsValue = TagsValue(),
    val emulationChosen: Pair<String, String>? = null,
    val typeOfDialog: Type? = null,
    val deletedMessage: Triple<String, String, Type>? = null,
    val techList: List<String> = listOf(),
    val intentFilter: List<IntentFilter> = listOf(),
    val pendingIntent: PendingIntent? = null,
    val nfcAdapter: NfcAdapter? = null
)

data class TagsValue(
    val message: String = "",
    val type: String = "Plain/Text",
    val messages: List<Pair<String, String>> = emptyList()
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
