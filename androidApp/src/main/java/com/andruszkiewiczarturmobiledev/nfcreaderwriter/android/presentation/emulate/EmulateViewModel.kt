package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.MainUiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class EmulateViewModel(

): KoinComponent {

    companion object {
        private val TAG = "EmulateViewModel_TAG"
    }

    private val _state = MutableStateFlow(EmulateState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<EmulateUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    fun onEvent(event: EmulateEvent) {
        when (event) {
            EmulateEvent.AddEmulateValue -> {
                if (_state.value.currentValue.message.isNotBlank()) {
                    _state.update { it.copy(
                        listOfValues = it.listOfValues + it.currentValue,
                        currentValue = it.currentValue.copy(
                            message = ""
                        )
                    ) }
                }
            }
            EmulateEvent.EmulateNFCCard -> {
                viewModelScope.launch {
                    if (_state.value.emulateChoose != null)
                        _sharedFlow.emit(EmulateUiEvent.EmulateValue(_state.value.emulateChoose!!.message))
                }
            }
            is EmulateEvent.EnteredEmulateValue -> {
                _state.update { it.copy(
                    currentValue = it.currentValue.copy(
                        message = event.value
                    )
                ) }
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
                    currentValue = it.currentValue.copy(
                        type = event.typeData
                    )
                ) }
            }
        }
    }
}