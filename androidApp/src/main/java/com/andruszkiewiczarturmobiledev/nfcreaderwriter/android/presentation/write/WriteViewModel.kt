package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WriteViewModel(): KoinComponent {

    companion object {
        private val TAG = "WriteViewModel_TAG"
    }

    private val _state = MutableStateFlow(WriteState())
    val state = _state.asStateFlow()

    fun onEvent(event: WriteEvent) {
        when(event) {
            WriteEvent.AddWriteValue -> {
                if (_state.value.currentValue.message.isNotBlank()) {
                    _state.update { it.copy(
                        listOfValues = it.listOfValues + it.currentValue,
                        currentValue = it.currentValue.copy(
                            message = ""
                        )
                    ) }
                }
            }
            is WriteEvent.EnteredWriteValue -> {
                _state.update { it.copy(
                    currentValue = it.currentValue.copy(
                        message = event.value
                    )
                ) }
            }
            WriteEvent.RemoveWriteValue -> {
                if (_state.value.deleteValue != null) {
                    _state.update { it.copy(
                        listOfValues = it.listOfValues.filter { v -> v != _state.value.deleteValue },
                    ) }
                    onEvent(WriteEvent.ShowDeletedDialog(null))
                }
            }
            is WriteEvent.SetTypeData -> {
                _state.update { it.copy(
                    currentValue = it.currentValue.copy(
                        type = event.typeData
                    )
                ) }
            }
            is WriteEvent.ShowDeletedDialog -> {
                _state.update { it.copy(
                    deleteValue = event.value
                ) }
            }
        }
    }
}