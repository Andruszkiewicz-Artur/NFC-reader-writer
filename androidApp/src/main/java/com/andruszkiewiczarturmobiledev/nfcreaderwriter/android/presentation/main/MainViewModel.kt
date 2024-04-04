package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.util.Locale

@OptIn(ExperimentalStdlibApi::class)
class MainViewModel(): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<MainUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val HEX_CHARS = "0123456789ABCDEF"

    companion object {
        private val TAG = "MainViewModel_TAG"
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.EnteredEmulateCardMessage -> {
                _state.update { it.copy(
                    emulateMessage = event.message
                ) }
            }
            is MainEvent.EnteredWriteMessage -> {
                _state.update { it.copy(
                    writeMessage = event.message
                ) }
            }
            MainEvent.AddWriteMessage -> {
                if (_state.value.writeMessage.isNotBlank())
                    _state.update { it.copy(
                        writeMessages = it.writeMessages + Pair(it.writeTypeMessage, it.writeMessage),
                        writeMessage = ""
                    ) }
            }
            is MainEvent.RemoveWriteMessage -> {
                _state.update { it.copy(
                    writeMessages = it.writeMessages.filter { message -> event.message != message}
                ) }
            }
            is MainEvent.OnClickSetAlertDialog -> {
                if (_state.value.typeOfDialog == Type.Emulate) {
                    viewModelScope.launch {
                        _sharedFlow.emit(MainUiEvent.StopEmulatingCard)
                    }
                }

                _state.update { it.copy(
                    typeOfDialog = event.type
                ) }
            }
            is MainEvent.SetIntentFilter -> {
                _state.update { it.copy(
                    intentFilter = event.intentFilter
                ) }
            }
            is MainEvent.SetNFCAdapter -> {
                _state.update { it.copy(
                    nfcAdapter = event.adapter
                ) }
            }
            is MainEvent.SetPendingIntent -> {
                _state.update { it.copy(
                    pendingIntent = event.pendingIntent
                ) }
            }
            is MainEvent.SetTechList -> {
                _state.update { it.copy(
                    techList = event.techList
                ) }
            }
            is MainEvent.SetReadState -> {
                _state.update { it.copy(
                    readCardState = event.readState
                ) }
            }
            is MainEvent.ReadNFCCard -> {
                val intent = event.intent
                var readState = NFCReadState()

                intent.let { newIntent ->
                    newIntent.getParcelableExtra<Tag?>(NfcAdapter.EXTRA_TAG)?.let { tag ->
                        val id = tag.id

                        readState.idTag = id.toHexString()
                        readState.sn = convertToColonSeparated(tag.id.toHexString())

                        tag.techList.forEach { tech ->
                            readState.techs = readState.techs + tech.split(".").lastOrNull() + ", "
                        }

                        readState.techs = readState.techs?.dropLast(2)?.replace("null", "")

                        tag.techList.forEach { tech ->
                            if (tech.equals(NfcA::class.java.name)) {
                                val nfcA = NfcA.get(tag)
                                val sak = nfcA.sak.toHexString()
                                val atqa = nfcA.atqa.toHexString()

                                readState.sak = sak
                                readState.atqa = atqa
                                readState.tagKind = "ISO 14443-3A"
                            } else if (tech.equals(NfcB::class.java.name)) {
                                val nfcB = NfcB.get(tag)

                                readState.tagKind = "ISO 14443-3B"
                            } else if (tech.equals(NfcF::class.java.name)) {
                                val nfcF = NfcF.get(tag)

                                readState.tagKind = "JIS 6319-4"
                                readState.systemCode = nfcF.systemCode.toHexString()
                            } else if (tech.equals(NfcV::class.java.name)) {
                                val nfcV = NfcV.get(tag)

                                readState.tagKind = "ISO 15693"
                                readState.systemCode = nfcV.dsfId.toHexString()
                            } else if (tech.equals(IsoDep::class.java.name)) {
                                val isoDep = IsoDep.get(tag)

                                readState.tagKind = "ISO 14443-4"
                            } else if (tech.equals(Ndef::class.java.name)) {
                                val ndef = Ndef.get(tag)

                                readState.isWritable = ndef.isWritable
                                readState.maxSizeStorage = ndef.maxSize.toString() + " bits"
                                readState.canSetOnlyToRead = ndef.canMakeReadOnly()

                            } else if (tech.equals(MifareClassic::class.java.name)) {
                                val mifareTag = MifareClassic.get(tag)

                                readState.dataFormat =
                                    MifareClassic::class.java.name.split(".").lastOrNull()
                            } else if (tech.equals(MifareUltralight::class.java.name)) {
                                val mifareUlTag = MifareUltralight.get(tag)

                                readState.dataFormat =
                                    MifareUltralight::class.java.name.split(".").lastOrNull()
                            }
                        }
                    }
                }

                if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                    readState.message = intent.dataString
                    readState.typeOfMessage = intent.type ?: intent.scheme

                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                        val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                        messages.forEach { message ->
                            val records = message.records

                            records.forEach { record ->
                                val stringMessage = String(record.payload)
                                val typeMessage = String(record.type)

                                readState.message = stringMessage
                                readState.typeOfMessage = typeMessage
                            }
                        }
                    }
                }

                onEvent(MainEvent.SetReadState(readState))
            }
            is MainEvent.WriteNFCCard -> {
                val intent = event.intent

                if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                    val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                    val ndef = Ndef.get(tag)

                    Log.d(TAG, "ndef value: $ndef")

                    if (ndef != null) {
                        Log.d(TAG, "ndef isWritable: ${ndef.isWritable}")
                        val records = _state.value.writeMessages.map { (type, message) -> createTextRecord(type, message) }.toTypedArray()

                        Log.d(TAG, "records: $records")
                        val messages = NdefMessage(
                            records
                        )



                        ndef.connect()
                        ndef.writeNdefMessage(messages)
                        ndef.close()

                        onEvent(MainEvent.EnteredWriteMessage(""))
                        onEvent(MainEvent.OnClickSetAlertDialog(null))
                    } else {

                    }
                }
            }
            MainEvent.EmulateNFCCard -> {
                viewModelScope.launch {
                    _sharedFlow.emit(MainUiEvent.EmulateCard(_state.value.emulateMessage))
                }
            }
        }
    }

    fun hexStringToByteArray(data: String) : ByteArray {

        val result = ByteArray(data.length / 2)

        for (i in 0 until data.length step 2) {
            val firstIndex = HEX_CHARS.indexOf(data[i]);
            val secondIndex = HEX_CHARS.indexOf(data[i + 1]);

            val octet = firstIndex.shl(4).or(secondIndex)
            result.set(i.shr(1), octet.toByte())
        }

        return result
    }

    private fun convertToColonSeparated(input: String): String {
        val chunks = input.chunked(2)
        return chunks.joinToString(":").uppercase()
    }

    private fun createTextRecord(mainType: String, value: String): NdefRecord {
        return NdefRecord.createMime(
            mainType,
            value.toByteArray(Charset.forName("US-ASCII"))
        )
    }
}