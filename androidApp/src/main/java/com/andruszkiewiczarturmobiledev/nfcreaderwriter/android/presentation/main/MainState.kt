package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter

data class MainState(
    val readCardState: NFCReadState? = null,
    val writeMessage: String = "",
    val emulateMessage: String = "",
    val typeOfDialog: Type? = null,
    val techList: List<String> = listOf(),
    val intentFilter: List<IntentFilter> = listOf(),
    val pendingIntent: PendingIntent? = null,
    val nfcAdapter: NfcAdapter? = null
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
    var message: String? = null,
    var typeOfMessage: String? = null
)
