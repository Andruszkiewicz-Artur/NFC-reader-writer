package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class EmulateViewModel(

): KoinComponent {

    companion object {
        private val TAG = "EmulateViewModel_TAG"
    }

    private val _state = MutableStateFlow(EmulateState())
    val state = _state.asStateFlow()

    fun onEvent(event: EmulateEvent) {
        when (event) {
            is EmulateEvent.AddEmulateValue -> {
                if (event.value.isNotBlank()) {
                    val newTagValue = TagValue(
                        type = _state.value.currentValue,
                        message = event.value
                    )
                    _state.update { it.copy(
                        listOfValues = it.listOfValues + newTagValue
                    ) }
                }
            }
            is EmulateEvent.FocusOnEmulatedValue -> {
                _state.update { it.copy(
                    emulateChoose = event.value
                ) }
            }
            EmulateEvent.RemoveEmulateValue -> {
                _state.update { it.copy(
                    emulateChoose = if (it.emulateChoose == _state.value.deleteValue) null else _state.value.emulateChoose,
                    listOfValues = it.listOfValues.filter { v -> v != _state.value.deleteValue},
                    deleteValue = null
                ) }
                onEvent(EmulateEvent.ShowDeletedDialog(null))
            }
            is EmulateEvent.ShowDeletedDialog -> {
                _state.update { it.copy(
                    deleteValue = event.value
                ) }
            }
            is EmulateEvent.SetTypeData -> {
                _state.update { it.copy(
                    currentValue = event.typeData
                ) }
            }
        }
    }
}