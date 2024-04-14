package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData

sealed class EmulateEvent {
    data class EnteredEmulateValue(val value: String): EmulateEvent()
    data class ShowDeletedDialog(val value: TagValue?): EmulateEvent()
    data class FocusOnEmulatedValue(val value: TagValue): EmulateEvent()
    data class SetTypeData(val typeData: TypeData): EmulateEvent()


    data object AddEmulateValue: EmulateEvent()
    data object RemoveEmulateValue: EmulateEvent()
}
