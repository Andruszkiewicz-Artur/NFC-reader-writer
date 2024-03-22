package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main

sealed class MainUiEvent {

    data class EmulateCard(val message: String): MainUiEvent()

}