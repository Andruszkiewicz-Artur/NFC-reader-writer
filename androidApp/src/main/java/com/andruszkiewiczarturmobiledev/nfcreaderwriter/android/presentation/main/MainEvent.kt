package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter

sealed class MainEvent {
    data class OnClickSetAlertDialog(val type: Type?): MainEvent()

    data class SetReadState(val readState: NFCReadState?): MainEvent()
    data class ReadNFCCard(val intent: Intent): MainEvent()
    data class SetWriteData(val value: List<TagValue>): MainEvent()
    data class WriteNFCCard(val intent: Intent): MainEvent()
    data class EmulateNFCCard(val message: String): MainEvent()
    data class ChangeStateOfInfoDialog(val isPresented: Boolean): MainEvent()
}
