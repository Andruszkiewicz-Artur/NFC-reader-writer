package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import android.annotation.SuppressLint
import android.app.PendingIntent
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
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.MyApplicationTheme
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCReadState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCWriteState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TopTabNav
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation.NavHostMain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.nio.charset.Charset

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
class MainActivity : ComponentActivity() {

    private var readNfcState = MutableStateFlow<NFCReadState?>(null)
    private var writeNfcState = MutableStateFlow(NFCWriteState())
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
            val navHostController = rememberNavController()

            MyApplicationTheme() {
                Surface() {
                    Scaffold(
                        topBar = {
                            Column {
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(text = "NFC App")
                                    }
                                )

                                TopTabNav(navHostController = navHostController)
                            }
                        }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .padding(padding)
                        ) {
                            if (nfcAdapter != null) {
                                if (nfcAdapter!!.isEnabled) {
                                    NavHostMain(
                                        nfcStateRead = readNfcState.collectAsState().value,
                                        nfcWriteState = writeNfcState.collectAsState().value,
                                        navHostController = navHostController,
                                        onClickSendMessage = {
                                            writeNfcState.update { it.copy(
                                                isDialog = true
                                            ) }
                                        },
                                        onClickDismissAlertDialog = {
                                            writeNfcState.update { it.copy(
                                                isDialog = false
                                            ) }
                                        },
                                        enteredMessage = { message ->
                                            writeNfcState.update { it.copy(
                                                message = message
                                            ) }
                                        }
                                    )
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

        val intent = Intent(this, this::class.java)
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            if (writeNfcState.value.message.isNotBlank()) writeNfcCard(intent)
            else readNfcCard(intent)
        }
        else readNfcState.update { null }
    }

    private fun init() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.let {
            Log.d(TAG, "$it")
        }

        foreGroundDispatchSystem()
    }

    private fun writeNfcCard(intent: Intent) {
        Log.d(TAG, "now work: writeNfcCart")
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(tag)

            if (ndef != null) {
                val message = NdefMessage(arrayOf(NdefRecord.createMime(
                    "plain/text",
                    writeNfcState.value.message.toByteArray(Charset.forName("US-ASCII")))))

                ndef.connect()
                ndef.writeNdefMessage(message)
                ndef.close()
            }

            writeNfcState.update { it.copy(
                message = "",
                isDialog = false
            ) }
        }
    }

    private fun readNfcCard(intent: Intent) {
        Log.d(TAG, "now work: readNfcCard")

        intent.let { newIntent ->
            readNfcState.update { NFCReadState() }
            newIntent.getParcelableExtra<Tag?>(NfcAdapter.EXTRA_TAG)?.let { tag ->
                val id = tag.id

                readNfcState.update { it?.copy(
                    idTag = id.toHexString(),
                    sn = convertToColonSeparated(tag.id.toHexString())
                ) }

                tag.techList.forEach { tech ->
                    readNfcState.update { it?.copy(
                        techs = it.techs + tech.split(".").lastOrNull() + ", "
                    ) }
                }

                readNfcState.update { it?.copy(
                    techs = it.techs?.dropLast(2)?.replace("null", "")
                ) }

                tag.techList.forEach { tech ->
                    if (tech.equals(NfcA::class.java.name)) {
                        val nfcA = NfcA.get(tag)
                        val sak = nfcA.sak.toHexString()
                        val atqa = nfcA.atqa.toHexString()

                        readNfcState.update { it?.copy(
                            sak = sak,
                            atqa = atqa,
                            tagKind = "ISO 14443-3A"
                        ) }
                    } else if (tech.equals(NfcB::class.java.name)) {
                        val nfcB = NfcB.get(tag)

                        readNfcState.update { it?.copy(
                            tagKind = "ISO 14443-3B"
                        ) }
                    } else if (tech.equals(NfcF::class.java.name)) {
                        val nfcF = NfcF.get(tag)

                        readNfcState.update { it?.copy(
                            tagKind = "JIS 6319-4",
                            systemCode = nfcF.systemCode.toHexString()
                        ) }
                    } else if (tech.equals(NfcV::class.java.name)) {
                        val nfcV = NfcV.get(tag)

                        readNfcState.update { it?.copy(
                            tagKind = "ISO 15693",
                            dsfId = nfcV.dsfId.toHexString()
                        ) }
                    } else if (tech.equals(IsoDep::class.java.name)) {
                        val isoDep = IsoDep.get(tag)

                        readNfcState.update { it?.copy(
                            tagKind = "ISO 14443-4"
                        ) }
                    } else if (tech.equals(Ndef::class.java.name)) {
                        val ndef = Ndef.get(tag)

                        readNfcState.update { it?.copy(
                            isWritable = ndef.isWritable,
                            maxSizeStorage = ndef.maxSize.toString() + " bits",
                            canSetOnlyToRead = ndef.canMakeReadOnly()
                        ) }
                    } else if (tech.equals(MifareClassic::class.java.name)) {
                        val mifareTag = MifareClassic.get(tag)

                        readNfcState.update { it?.copy(
                            dataFormat = MifareClassic::class.java.name.split(".").lastOrNull()
                        ) }
                    } else if (tech.equals(MifareUltralight::class.java.name)) {
                        val mifareUlTag = MifareUltralight.get(tag)

                        readNfcState.update { it?.copy(
                            dataFormat = MifareUltralight::class.java.name.split(".").lastOrNull()
                        ) }
                    }
                }
            }
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            Log.d(TAG, "Start reading message")

            readNfcState.update { it?.copy(
                message = intent.dataString,
                typeOfMessage = intent.type ?: intent.scheme
            ) }

            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                messages.forEach { message ->
                    val records = message.records

                    records.forEach { record ->
                        val stringMessage = String(record.payload)
                        val typeMessage = String(record.type)

                        readNfcState.update { it?.copy(
                            message = stringMessage,
                            typeOfMessage = typeMessage
                        ) }
                    }
                }
            }
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
