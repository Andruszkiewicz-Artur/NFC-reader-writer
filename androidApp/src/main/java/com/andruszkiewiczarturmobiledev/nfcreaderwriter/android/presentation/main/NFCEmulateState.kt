package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

data class NFCEmulateState(
    val message: String = "Emulated value",
    val type: String = "text/plain",
    val isDialog: Boolean = false
)
