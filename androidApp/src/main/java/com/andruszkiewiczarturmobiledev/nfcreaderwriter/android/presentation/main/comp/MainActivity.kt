package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.domain.service.MyHostApduService
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainUiEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.comp.BottomTabBar
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation.NavHostMain
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.MyApplicationTheme
import kotlinx.coroutines.flow.collectLatest
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.R.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
class MainActivity : ComponentActivity(), NfcAdapter.ReaderCallback {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var state: MainState

    companion object {
        private val TAG = "MainActivity_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state = viewModel.state.value
        val intent = Intent(this@MainActivity, MyHostApduService::class.java)

        setContent {
            val navHostController = rememberNavController()
            state = viewModel.state.collectAsState().value

            LaunchedEffect(key1 = true) {
                viewModel.sharedFlow.collectLatest { event ->
                    when (event) {
                        is MainUiEvent.EmulateCard -> {
                            intent.putExtra("ndefMessage", event.message)
                            startService(intent)
                        }
                        MainUiEvent.StopEmulatingCard -> {
                            stopService(intent)
                        }
                        is MainUiEvent.Toast -> {
                            Toast.makeText(applicationContext, event.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            MyApplicationTheme() {
                Surface() {
                    Scaffold(
                        topBar = {
                            Column {
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(text = stringResource(id = string.NFCApp))
                                    },
                                    actions = {
                                        IconButton(onClick = { viewModel.onEvent(MainEvent.ChangeStateOfInfoDialog(true)) }) {
                                            Icon(
                                                imageVector = Icons.Outlined.Info,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            BottomTabBar(navHostController = navHostController)
                        }
                    ) { padding ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            if (state.nfcAdapter == null) {
                                Text(
                                    text = stringResource(id = string.DontSupportNFC),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } else if (!state.nfcAdapter!!.isEnabled) {
                                Text(
                                    text = stringResource(id = string.TurnOnNFC),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                NavHostMain(
                                    navHostController = navHostController,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }

                TypeSavingDialog(
                    type = state.typeOfDialog,
                    onClickDismissDialog = {
                        viewModel.onEvent(MainEvent.OnClickSetAlertDialog(null))
                    }
                )

                DeleteDialog(
                    message = state.deletedMessage,
                    onClickDismissButton = {
                        viewModel.onEvent(MainEvent.ShowDeletedDialog(null))
                    },
                    onClickConfirmButton = {
                        if (state.deletedMessage?.third == Type.Emulate) {
                            viewModel.onEvent(MainEvent.RemoveEmulateMessage(Pair(state.deletedMessage!!.first, state.deletedMessage!!.second)))
                        } else if (state.deletedMessage?.third == Type.Write) {
                            viewModel.onEvent(MainEvent.RemoveWriteMessage(Pair(state.deletedMessage!!.first, state.deletedMessage!!.second)))
                        }
                    }
                )

                InfoDialog(
                    isPresented = state.isPresentedInfoDialog,
                    onClickDismiss = { viewModel.onEvent(MainEvent.ChangeStateOfInfoDialog(false)) }
                )
            }
        }

        init()
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

    override fun onDestroy() {
        super.onDestroy()
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

        Log.d(TAG, "on new Intent value in main: $intent")

        if (intent != null) {
            if (state.typeOfDialog == Type.Write) viewModel.onEvent(MainEvent.WriteNFCCard(intent))
            else viewModel.onEvent(MainEvent.ReadNFCCard(intent))
        }
        else viewModel.onEvent(MainEvent.SetReadState(null))
    }

    private fun init() {
        viewModel.onEvent(MainEvent.SetNFCAdapter(NfcAdapter.getDefaultAdapter(this)))


        foreGroundDispatchSystem()
    }

    private fun foreGroundDispatchSystem() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        viewModel.onEvent(MainEvent.SetPendingIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)))

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        viewModel.onEvent(MainEvent.SetIntentFilter(listOf(ndef)))
        viewModel.onEvent(MainEvent.SetTechList(
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
