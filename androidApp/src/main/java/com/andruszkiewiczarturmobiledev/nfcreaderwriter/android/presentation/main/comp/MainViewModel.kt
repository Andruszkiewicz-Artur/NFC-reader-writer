package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.comp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainState
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainUiEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(): ViewModel() {

    val state = MutableStateFlow(MainState())

    private val HEX_CHARS = "0123456789ABCDEF"

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.EnteredEmulateCardMessage -> {
                state.update { it.copy(
                    emulateMessage = event.message
                ) }
            }
            is MainUiEvent.EnteredWriteMessage -> {
                state.update { it.copy(
                    writeMessage = event.message
                ) }
            }
            is MainUiEvent.OnClickSetAlertDialog -> {
                state.update { it.copy(
                    typeOfDialog = event.type
                ) }

                if (event.type == Type.Write) {

                }
            }
            is MainUiEvent.SetIntentFilter -> {
                state.update { it.copy(
                    intentFilter = event.intentFilter
                ) }
            }
            is MainUiEvent.SetNFCAdapter -> {
                state.update { it.copy(
                    nfcAdapter = event.adapter
                ) }
            }
            is MainUiEvent.SetPendingIntent -> {
                state.update { it.copy(
                    pendingIntent = event.pendingIntent
                ) }
            }
            is MainUiEvent.SetTechList -> {
                state.update { it.copy(
                    techList = event.techList
                ) }
            }
            is MainUiEvent.SetReadState -> {
                state.update { it.copy(
                    readCardState = event.readState
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

    private val HEX_CHARS_ARRAY = "0123456789ABCDEF".toCharArray()

    fun convertToColonSeparated(input: String): String {
        val chunks = input.chunked(2)
        return chunks.joinToString(":").uppercase()
    }
}