package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TypeData

data class WriteState (
    val currentValue: TypeData = TypeData.PlainText,
    val listOfValues: List<TagValue> = emptyList(),
    val deleteValue: TagValue? = null
)
