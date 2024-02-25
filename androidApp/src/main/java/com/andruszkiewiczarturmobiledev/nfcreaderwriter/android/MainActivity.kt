package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
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
import android.nfc.tech.TagTechnology
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.read.comp.ReadViewPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.nio.charset.Charset
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import kotlin.experimental.and

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
class MainActivity : ComponentActivity() {

    private var nfcState = MutableStateFlow<NFCState?>(null)
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<String>

    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        setContent {
            MyApplicationTheme() {
                Surface() {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(text = "NFC App")
                                }
                            )
                        }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .padding(padding)
                        ) {
                            if (nfcAdapter != null) {
                                if (nfcAdapter!!.isEnabled) {
                                    ReadViewPresentation(state = nfcState.collectAsState().value)
                                } else {
                                    Text(text = "You need to turn the nfc on your phone")
                                }
                            } else {
                                Text(text = "Your device don`t support nfc communication")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onResume() {
        super.onResume()

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, arrayOf(techListsArray))
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) processIntent(intent)
        else nfcState.update { null }
    }

    private fun init() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.let {
            Log.d(TAG, "$it")
        }

        foreGroundDispatchSystem()
    }

    private fun processIntent(intent: Intent) {
        intent.let { newIntent ->
            nfcState.update { NFCState() }
            newIntent.getParcelableExtra<Tag?>(NfcAdapter.EXTRA_TAG)?.let { tag ->
                val id = tag.id

                nfcState.update { it?.copy(
                    idTag = id.toHexString(),
                    sn = convertToColonSeparated(tag.id.toHexString())
                ) }

                tag.techList.forEach { tech ->
                    nfcState.update { it?.copy(
                        techs = it.techs + tech.split(".").lastOrNull() + ", "
                    ) }
                }

                nfcState.update { it?.copy(
                    techs = it.techs?.dropLast(2)?.replace("null", "")
                ) }

                tag.techList.forEach { tech ->
                    if (tech.equals(NfcA::class.java.name)) {
                        val nfcA = NfcA.get(tag)
                        val sak = nfcA.sak.toHexString()
                        val atqa = nfcA.atqa.toHexString()

                        nfcState.update { it?.copy(
                            sak = sak,
                            atqa = atqa,
                            tagKind = "ISO 14443-3A"
                        ) }
                    } else if (tech.equals(NfcB::class.java.name)) {
                        val nfcB = NfcB.get(tag)

                        nfcState.update { it?.copy(
                            tagKind = "ISO 14443-3B"
                        ) }
                    } else if (tech.equals(NfcF::class.java.name)) {
                        val nfcF = NfcF.get(tag)

                        nfcState.update { it?.copy(
                            tagKind = "JIS 6319-4",
                            systemCode = nfcF.systemCode.toHexString()
                        ) }
                    } else if (tech.equals(NfcV::class.java.name)) {
                        val nfcV = NfcV.get(tag)

                        nfcState.update { it?.copy(
                            tagKind = "ISO 15693",
                            dsfId = nfcV.dsfId.toHexString()
                        ) }
                    } else if (tech.equals(IsoDep::class.java.name)) {
                        val isoDep = IsoDep.get(tag)

                        nfcState.update { it?.copy(
                            tagKind = "ISO 14443-4"
                        ) }
                    } else if (tech.equals(Ndef::class.java.name)) {
                        val ndef = Ndef.get(tag)

                        nfcState.update { it?.copy(
                            isWritable = ndef.isWritable,
                            maxSizeStorage = ndef.maxSize.toString() + " bits",
                            canSetOnlyToRead = ndef.canMakeReadOnly()
                        ) }
                    } else if (tech.equals(MifareClassic::class.java.name)) {
                        val mifareTag = MifareClassic.get(tag)

                        nfcState.update { it?.copy(
                            dataFormat = MifareClassic::class.java.name.split(".").lastOrNull(),
                            storage = "${mifareTag.sectorCount} bits",
                        ) }
                    } else if (tech.equals(MifareUltralight::class.java.name)) {
                        val mifareUlTag = MifareUltralight.get(tag)

                        nfcState.update { it?.copy(
                            dataFormat = MifareUltralight::class.java.name.split(".").lastOrNull()
                        ) }
                    }
                }
            }

            if (NfcAdapter.ACTION_NDEF_DISCOVERED == newIntent.action) {
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                    val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                    messages.forEach { message ->
                        val records = message.records

                        if (records != null && records.isNotEmpty()) {
                            val messageFromRecord = records[0]
                            val originalMessage = getTextFromNdefRecord(messageFromRecord)
                            nfcState.update { it?.copy(
                                playText = originalMessage
                            ) }
                        }
                    }
                }
            }

            Log.d(TAG, "state: ${nfcState.value}")
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.action)) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                for (rawMessage in rawMessages.map { it as NdefMessage }) {
                    for (record in rawMessage.records) {
                        if (record.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(record.type, NdefRecord.RTD_URI)) {
                            val uri = record.toUri()
                            if ("tel".equals(uri.scheme)) {
                                val phoneNumber = uri.schemeSpecificPart
                                nfcState.update { it?.copy(
                                    phoneNumber = phoneNumber
                                ) }
                            }
                        }
                    }
                }
            }
        }
    }



    private fun getTextFromNdefRecord(record: NdefRecord): String? {
        return try {
            val payload = record.payload
            val textEncoding = if ((payload[0] and 0) == 0.toByte()) "UTF-8" else "UTF-16"
            val languageSize = payload[0] and 63
            val tagContent = String(payload, languageSize + 1, payload.size - languageSize - 1, Charset.forName(textEncoding))
            nfcState.update { it?.copy(
                transformationFormat = textEncoding
            ) }
            tagContent
        } catch (e: Exception) {
            Log.e(TAG, "problem with convert message from tag")
            null
        }
    }

    private fun convertToColonSeparated(input: String): String {
        val chunks = input.chunked(2)
        return chunks.joinToString(":").uppercase()
    }

    private fun foreGroundDispatchSystem() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        intentFiltersArray = arrayOf(ndef)
        techListsArray = arrayOf(
            NfcA::class.java.name,
            NfcB::class.java.name,
            NfcF::class.java.name,
            NfcV::class.java.name,
            IsoDep::class.java.name,
            MifareClassic::class.java.name,
            MifareUltralight::class.java.name
        )
    }
}
