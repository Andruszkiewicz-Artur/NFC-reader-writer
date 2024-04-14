package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

import android.content.Intent
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
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material.icons.rounded.WifiPassword
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.nio.charset.Charset

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

    init {
        fillDataTypes()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.EnteredWriteMessage -> {
                _state.update { it.copy(
                    writeState = it.writeState.copy(
                        message = event.message
                    )
                ) }
            }
            MainEvent.AddWriteMessage -> {
                viewModelScope.launch {
                    if (_state.value.writeState.message.isBlank()) {
                        _sharedFlow.emit(MainUiEvent.Toast("Field is empty!"))
                    } else if (_state.value.writeStateList.contains(_state.value.writeState)) {
                        _sharedFlow.emit(MainUiEvent.Toast("You have this value already!"))
                    } else {
                        _state.update { it.copy(
                            writeStateList = it.writeStateList + it.writeState,
                            writeState = it.writeState.copy(
                                message = ""
                            )
                        ) }
                    }
                }
            }
            is MainEvent.RemoveWriteMessage -> {
                _state.update { it.copy(
                    writeStateList = it.writeStateList.filter { message -> event.value != message },
                    deletedMessage = null
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
                val readState = readCardInfo(intent = intent)
                readState.messages = readDataFromCard(intent)

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
                        val records = _state.value.writeStateList.map { value -> createTextRecord(value.type.toString(), value.message) }.toTypedArray()

                        Log.d(TAG, "records: $records")
                        val messages = NdefMessage(
                            records
                        )

                        ndef.connect()
                        ndef.writeNdefMessage(messages)
                        ndef.close()

                        onEvent(MainEvent.EnteredWriteMessage(""))
                        onEvent(MainEvent.OnClickSetAlertDialog(null))
                    }
                }
            }
            is MainEvent.EmulateNFCCard -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        typeOfDialog = Type.Emulate
                    ) }
                    _sharedFlow.emit(MainUiEvent.EmulateCard(event.message))
                }
            }
            is MainEvent.ShowDeletedDialog -> {
                if(event.value != null && event.type != null) {
                    _state.update { it.copy(
                        deletedMessage = Pair(event.value, event.type)
                    ) }
                } else {
                    _state.update { it.copy(
                        deletedMessage = null
                    ) }
                }
            }
            is MainEvent.ChangeStateOfInfoDialog -> {
                _state.update { it.copy(
                    isPresentedInfoDialog = event.isPresented
                ) }
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

    private fun readDataFromCard(intent: Intent): List<Pair<String, String>> {
        val messagesState = mutableListOf<Pair<String, String>>()

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                Log.d(TAG, "messages from read: $messages")
                messages.forEach { message ->
                    val records = message.records
                    Log.d(TAG, "records from read: $records")

                    records.forEach { record ->
                        Log.d(TAG, "record from read: $record")
                        val stringMessage = String(record.payload)
                        val typeMessage = String(record.type)

                        messagesState.add(Pair(typeMessage, stringMessage))
                    }
                }
            }
        }

        return messagesState
    }

    private fun readCardInfo(readState: NFCReadState = NFCReadState(), intent: Intent): NFCReadState {
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

        return readState
    }

    private fun fillDataTypes() {
        val data = listOf(
            TypeDataState(
                type = TypeData.PlainText,
                name = R.string.PlainText,
                icon = Icons.Rounded.Description,
                description = R.string.PlainTextDescription
            ),
            TypeDataState(
                type = TypeData.URLURI,
                name = R.string.URLURI,
                icon = Icons.Rounded.Link,
                description = R.string.URLURIDescription
            ),
            TypeDataState(
                type = TypeData.OwnURLURI,
                name = R.string.OwnURLURI,
                icon = Icons.Rounded.DatasetLinked,
                description = R.string.OwnURLURIDescription
            ),
            TypeDataState(
                type = TypeData.Search,
                name = R.string.Search,
                icon = Icons.Rounded.Search,
                description = R.string.SearchDescription
            ),
            TypeDataState(
                type = TypeData.SocialNetwork,
                name = R.string.SocialNetwork,
                icon = Icons.Rounded.Diversity3,
                description = R.string.SocialNetworkDescription
            ),
            TypeDataState(
                type = TypeData.Video,
                name = R.string.Video,
                icon = Icons.Rounded.OndemandVideo,
                description = R.string.VideoDescription
            ),
            TypeDataState(
                type = TypeData.File,
                name = R.string.File,
                icon = Icons.Rounded.FileCopy,
                description = R.string.FileDescription
            ),
            TypeDataState(
                type = TypeData.Application,
                name = R.string.Application,
                icon = Icons.Rounded.Smartphone,
                description = R.string.ApplicationDescription
            ),
            TypeDataState(
                type = TypeData.Email,
                name = R.string.Email,
                icon = Icons.Rounded.Email,
                description = R.string.EmailDescription
            ),
            TypeDataState(
                type = TypeData.Contact,
                name = R.string.Contact,
                icon = Icons.Outlined.ContactPage,
                description = R.string.ContactDescription
            ),
            TypeDataState(
                type = TypeData.PhoneNumber,
                name = R.string.PhoneNumber,
                icon = Icons.Outlined.Call,
                description = R.string.PhoneNumberDescription
            ),
            TypeDataState(
                type = TypeData.SMS,
                name = R.string.SMS,
                icon = Icons.Outlined.Sms,
                description = R.string.SMSDescription
            ),
            TypeDataState(
                type = TypeData.Location,
                name = R.string.Location,
                icon = Icons.Outlined.LocationOn,
                description = R.string.LocationDescription
            ),
            TypeDataState(
                type = TypeData.OwnLocation,
                name = R.string.OwnLocation,
                icon = Icons.Outlined.MyLocation,
                description = R.string.OwnLocationDescription
            ),
            TypeDataState(
                type = TypeData.Address,
                name = R.string.Address,
                icon = Icons.Outlined.LocalLibrary,
                description = R.string.AddressDescription
            ),
            TypeDataState(
                type = TypeData.WiFi,
                name = R.string.WiFi,
                icon = Icons.Rounded.WifiPassword,
                description = R.string.WiFiDescription
            ),
            TypeDataState(
                type = TypeData.Data,
                name = R.string.Data,
                icon = Icons.Outlined.Storage,
                description = R.string.DataDescription
            )
        )

        _state.update { it.copy(
            typesOfData = data
        ) }
    }
}