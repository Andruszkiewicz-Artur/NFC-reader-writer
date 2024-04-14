package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write

import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.main.TagValue

data class WriteState (
    val currentValue: TagValue = TagValue(),
    val listOfValues: List<TagValue> = emptyList(),
    val deleteValue: TagValue? = null
)
