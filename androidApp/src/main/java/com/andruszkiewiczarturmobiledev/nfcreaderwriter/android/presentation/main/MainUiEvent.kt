package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.app.PendingIntent
import android.content.IntentFilter
import android.nfc.NfcAdapter

sealed class MainUiEvent {
    data class OnClickSetAlertDialog(val type: Type?): MainUiEvent()
    data class EnteredWriteMessage(val message: String): MainUiEvent()
    data class EnteredEmulateCardMessage(val message: String): MainUiEvent()
    data class SetNFCAdapter(val adapter: NfcAdapter): MainUiEvent()
    data class SetPendingIntent(val pendingIntent: PendingIntent): MainUiEvent()
    data class SetIntentFilter(val intentFilter: List<IntentFilter>): MainUiEvent()
    data class SetTechList(val techList: List<String>): MainUiEvent()
    data class SetReadState(val readState: NFCReadState?): MainUiEvent()
}

enum class Type {
    Write,
    Emulate
}
