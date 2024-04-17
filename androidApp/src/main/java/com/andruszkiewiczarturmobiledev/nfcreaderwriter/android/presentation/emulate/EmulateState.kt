package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData

data class EmulateState(
    val currentValue: TypeData = TypeData.PlainText,
    val listOfValues: List<TagValue> = emptyList(),
    val emulateChoose: TagValue? = null,
    val deleteValue: TagValue? = null
)
