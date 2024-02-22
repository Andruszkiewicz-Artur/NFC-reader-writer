package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android

import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.NfcA
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.nio.charset.Charset
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.experimental.and

class MainActivity : ComponentActivity() {

    private var value = mutableStateOf("nothing")
    private var nfcAdapter: NfcAdapter? = null

    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.let {
            Log.d(TAG, "$it")
        }

        setContent {
            MyApplicationTheme() {
                Surface {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                    ) {
                        Column {
                            Text(
                                text = "Nfc tag: ${value.value}"
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(TAG, "intent: $intent")

//        getIntent().let {
//            val tag: NdefMessage? = it.getParcelableExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
//            Log.d(TAG, "intent inner: $it")
//            Log.d(TAG, "tag: $tag")
//        }

        intent?.let { newIntent ->
            newIntent.getParcelableExtra<Tag?>(NfcAdapter.EXTRA_TAG)?.let { tag ->
                val stringBuilder: StringBuilder = java.lang.StringBuilder()
                val id = tag.id

                stringBuilder.append("TAG ID: $id")
                stringBuilder.append("TAG ID(hex): ${getHex(id)}\n")
                stringBuilder.append("TAG ID(dec): ${getDec(id)}\n")
                stringBuilder.append("TAG ID(reversed): ${getReversed(id)}\n")
                stringBuilder.append("Technologies: ")
                tag.techList.forEach { stringBuilder.append("$it, ") }
                stringBuilder.append("\n")

                tag.techList.forEach { tech ->
                    if (tech.equals(MifareClassic::class.java.name)) {
                        stringBuilder.append("\n")
                        val mifareTag: MifareClassic = MifareClassic.get(tag)
                        val type : String
                        if (mifareTag.getType() == MifareClassic.TYPE_CLASSIC) type = "Classic"
                        else if (mifareTag.getType() == MifareClassic.TYPE_PLUS) type = "Plus"
                        else if (mifareTag.getType() == MifareClassic.TYPE_PRO) type = "Pro"
                        else type = "Unknown"
                        stringBuilder.append("Mifare Classic type: $type \n")
                        stringBuilder.append("Mifare size: ${mifareTag.size} bytes \n")
                        stringBuilder.append("Mifare sectors: ${mifareTag.sectorCount} \n")
                        stringBuilder.append("Mifare blocks: ${mifareTag.blockCount}")
                    }
                    if (tech.equals(MifareUltralight::class.java.name)) {
                        stringBuilder.append('\n');
                        val mifareUlTag : MifareUltralight = MifareUltralight.get(tag);
                        val type : String
                        if (mifareUlTag.getType() == MifareUltralight.TYPE_ULTRALIGHT) type = "Ultralight"
                        else if (mifareUlTag.getType() == MifareUltralight.TYPE_ULTRALIGHT_C) type = "Ultralight C"
                        else type = "Unkown"
                        stringBuilder.append("Mifare Ultralight type: ");
                        stringBuilder.append(type)
                    }
                }

                Log.d(TAG, "$stringBuilder")
            }

            if (NfcAdapter.ACTION_NDEF_DISCOVERED == newIntent.action) {
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                    val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                    messages.forEach { message ->
                        val records = message.records

                        if (records != null && records.isNotEmpty()) {
                            val messageFromRecord = records[0]
                            val originalMessage = getTextFromNdefRecord(messageFromRecord)
                            value.value = originalMessage ?: "nothing"
                        }
                    }
                }
            }
        }
    }

    private fun getDateTimeNow() : String { Log.d(TAG, "getDateTimeNow()")
        val TIME_FORMAT : DateFormat = SimpleDateFormat.getDateTimeInstance()
        val now : Date = Date()
        Log.d(ContentValues.TAG,"getDateTimeNow() Return ${TIME_FORMAT.format(now)}")
        return TIME_FORMAT.format(now)
    }

    private fun getHex(bytes : ByteArray) : String {
        val sb = StringBuilder()
        for (i in bytes.indices.reversed()) {
            val b : Int = bytes[i].and(0xff.toByte()).toInt()
            if (b < 0x10) sb.append('0')
            sb.append(Integer.toHexString(b))
            if (i > 0)
                sb.append(" ")
        }
        return sb.toString()
    }

    private fun getDec(bytes : ByteArray) : Long {
        Log.d(TAG, "getDec()")
        var result : Long = 0
        var factor : Long = 1
        for (i in bytes.indices) {
            val value : Long = bytes[i].and(0xffL.toByte()).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    private fun getReversed(bytes : ByteArray) : Long {
        Log.d(TAG, "getReversed()")
        var result : Long = 0
        var factor : Long = 1
        for (i in bytes.indices.reversed()) {
            val value = bytes[i].and(0xffL.toByte()).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    private fun getTextFromNdefRecord(record: NdefRecord): String? {
        val stringBuilder = StringBuilder()
        return try {
            val payload = record.payload
            val textEncoding = if ((payload[0] and 0) == 0.toByte()) "UTF-8" else "UTF-16"
            val languageSize = payload[0] and 63
            val tagContent = String(payload, languageSize + 1, payload.size - languageSize - 1, Charset.forName(textEncoding))
            tagContent
        } catch (e: Exception) {
            Log.e(TAG, "problem with convert message from tag")
            null
        }
    }
}
