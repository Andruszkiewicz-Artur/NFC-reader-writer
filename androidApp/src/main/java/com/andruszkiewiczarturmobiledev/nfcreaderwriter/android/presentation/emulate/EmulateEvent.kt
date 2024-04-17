package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData

sealed class EmulateEvent {
    data class ShowDeletedDialog(val value: TagValue?): EmulateEvent()
    data class FocusOnEmulatedValue(val value: TagValue): EmulateEvent()
    data class SetTypeData(val typeData: TypeData): EmulateEvent()
    data class AddEmulateValue(val value: String): EmulateEvent()

    data object RemoveEmulateValue: EmulateEvent()
}
