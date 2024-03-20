package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.cardemulation.HostApduService
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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.constraintlayout.core.motion.utils.Utils
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.domain.service.MyHostApduService
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainUiEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.NFCReadState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.TopTabNav
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation.NavHostMain
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.MyApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.nio.charset.Charset

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
class MainActivity : ComponentActivity(), NfcAdapter.ReaderCallback {

    private val viewModel: MainViewModel by viewModels()
    private val state = viewModel.state.asStateFlow().value


    companion object {
        private val TAG = "MainActivity"
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
                            if (state.nfcAdapter != null) {
                                if (state.nfcAdapter.isEnabled) {
                                    NavHostMain(
                                        navHostController = navHostController,
                                        viewModel = viewModel
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

        state.nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onResume() {
        super.onResume()

        val intent = Intent(this, this::class.java)
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        state.nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        val response = isoDep.transceive(viewModel.hexStringToByteArray(
            "00A4040007A0000002471001"))
        Log.d(TAG, "Card Response: ${response}")
        isoDep.close()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            if (state.writeMessage.isNotBlank()) writeNfcCard(intent)
            else readNfcCard(intent)
        }
        else viewModel.onEvent(MainUiEvent.SetReadState(null))
    }

    private fun init() {
        viewModel.onEvent(MainUiEvent.SetNFCAdapter(NfcAdapter.getDefaultAdapter(this)))

        foreGroundDispatchSystem()
    }

    private fun writeNfcCard(intent: Intent) {
        Log.d(TAG, "now work: writeNfcCart")
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(tag)

            if (ndef != null) {
                val message = NdefMessage(
                    arrayOf(
                        NdefRecord.createMime(
                            "plain/text",
                            state.writeMessage.toByteArray(Charset.forName("US-ASCII"))
                        )
                    )
                )

                ndef.connect()
                ndef.writeNdefMessage(message)
                ndef.close()
            }

            viewModel.onEvent(MainUiEvent.EnteredWriteMessage(""))
            viewModel.onEvent(MainUiEvent.OnClickSetAlertDialog(null))
        }
    }

    private fun readNfcCard(intent: Intent) {
        var readState = NFCReadState()

        intent.let { newIntent ->
            newIntent.getParcelableExtra<Tag?>(NfcAdapter.EXTRA_TAG)?.let { tag ->
                val id = tag.id

                readState.idTag = id.toHexString()
                readState.sn = viewModel.convertToColonSeparated(tag.id.toHexString())

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
            Log.d(TAG, "Start reading message")

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

        viewModel.onEvent(MainUiEvent.SetReadState(readState))
    }

    private fun foreGroundDispatchSystem() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        viewModel.onEvent(MainUiEvent.SetPendingIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)))

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        viewModel.onEvent(MainUiEvent.SetIntentFilter(listOf(ndef)))
        viewModel.onEvent(MainUiEvent.SetTechList(
            listOf(
                NfcA::class.java.name,
                NfcB::class.java.name,
                NfcF::class.java.name,
                NfcV::class.java.name,
                IsoDep::class.java.name,
                MifareClassic::class.java.name,
                MifareUltralight::class.java.name
            )
        ))
    }
}
