package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
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
    private lateinit var intent: Intent
    private var nfcAdapter: NfcAdapter? = null

    companion object {
        private val TAG = "MainActivity_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

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
                    }
                }
            }

            MyApplicationTheme {
                Surface {
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
                            if (nfcAdapter == null) {
                                Text(
                                    text = stringResource(id = string.DontSupportNFC),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } else if (!nfcAdapter!!.isEnabled) {
                                Text(
                                    text = stringResource(id = string.TurnOnNFC),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                NavHostMain(
                                    navHostController = navHostController,
                                    nfcReadState = state.readCardState,
                                    emulateNFCCard = { message ->
                                        viewModel.onEvent(MainEvent.EmulateNFCCard(message))
                                    },
                                    writeNFCCard = { listOfMessages ->
                                        viewModel.onEvent(MainEvent.SetWriteData(listOfMessages))
                                    }
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

                InfoDialog(
                    isPresented = state.isPresentedInfoDialog,
                    onClickDismiss = { viewModel.onEvent(MainEvent.ChangeStateOfInfoDialog(false)) }
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if (nfcAdapter != null)
            nfcAdapter!!.disableForegroundDispatch(this)
    }

    override fun onResume() {
        super.onResume()

        val intent = Intent(this, this::class.java)
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        if (nfcAdapter != null)
            nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (nfcAdapter != null)
            nfcAdapter!!.disableForegroundDispatch(this)
    }

    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        isoDep.close()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            if (state.typeOfDialog == Type.Write) viewModel.onEvent(MainEvent.WriteNFCCard(intent))
            else viewModel.onEvent(MainEvent.ReadNFCCard(intent))
        }
        else viewModel.onEvent(MainEvent.SetReadState(null))
    }

    private fun init() {
        state = viewModel.state.value
        intent = Intent(this@MainActivity, MyHostApduService::class.java)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }
}
