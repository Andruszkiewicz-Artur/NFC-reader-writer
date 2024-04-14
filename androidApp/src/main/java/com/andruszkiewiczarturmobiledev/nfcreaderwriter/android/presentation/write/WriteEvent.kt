package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateEvent
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData

sealed class WriteEvent {
    data class EnteredWriteValue(val value: String): WriteEvent()
    data class ShowDeletedDialog(val value: TagValue?): WriteEvent()
    data class SetTypeData(val typeData: TypeData): WriteEvent()


    data object AddWriteValue: WriteEvent()
    data object RemoveWriteValue: WriteEvent()
}
