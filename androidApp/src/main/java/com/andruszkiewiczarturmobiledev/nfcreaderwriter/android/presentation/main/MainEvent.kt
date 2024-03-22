package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter

sealed class MainEvent {
    data class OnClickSetAlertDialog(val type: Type?): MainEvent()
    data class EnteredWriteMessage(val message: String): MainEvent()
    data class EnteredEmulateCardMessage(val message: String): MainEvent()
    data class SetNFCAdapter(val adapter: NfcAdapter): MainEvent()
    data class SetPendingIntent(val pendingIntent: PendingIntent): MainEvent()
    data class SetIntentFilter(val intentFilter: List<IntentFilter>): MainEvent()
    data class SetTechList(val techList: List<String>): MainEvent()
    data class SetReadState(val readState: NFCReadState?): MainEvent()
    data class ReadNFCCard(val intent: Intent): MainEvent()
    data class WriteNFCCard(val intent: Intent): MainEvent()
    data object EmulateNFCCard: MainEvent()
}

enum class Type {
    Write,
    Emulate
}
