package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.Greeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private val tagState = MutableStateFlow<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicjalizacja NFC adaptera
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // Tworzenie PendingIntent, które będzie wywoływane po wykryciu NFC tagu
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        setContent {
            MyApplicationTheme {
                Text(text = tagState.value ?: "Empty value")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Aktywowanie odczytywania NFC tagów
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        // Deaktywowanie odczytywania NFC tagów
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action) {
                // Pobieranie tagu NFC z intencji
                val tag: Tag? = it.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                tag?.let { nfcTag ->
                    // Odczytywanie danych z tagu NFC
                    val ndef = Ndef.get(nfcTag)
                    ndef?.let { ndefTag ->
                        ndefTag.connect()
                        val ndefMessage = ndefTag.ndefMessage
                        val payload = ndefMessage.records[0].payload
                        val text = String(payload)
                        // Wyświetlanie odczytanych danych
                        println("NFC Tag content: $text")
                        tagState.update { text }
                        ndefTag.close()
                    }
                }
            }
        }
    }
}
