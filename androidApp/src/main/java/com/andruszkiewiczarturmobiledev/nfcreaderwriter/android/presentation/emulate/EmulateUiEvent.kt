package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate

sealed class EmulateUiEvent {
    data class EmulateValue(val message: String): EmulateUiEvent()
}