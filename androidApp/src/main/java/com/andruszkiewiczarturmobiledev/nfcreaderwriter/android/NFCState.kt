package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android

data class NFCState(
    val tagKind: String? = null,
    val idTag: String? = null,
    val techs: String? = null,
    val sn: String? = null,
    val systemCode: String? = null,
    val dsfId: String? = null,
    val maxSizeStorage: String? = null,
    val atqa: String? = null,
    val sak: String? = null,
    val dataFormat: String? = null,
    val storage: String? = null,
    val canSetOnlyToRead: Boolean? = null,
    val isWritable: Boolean? = null,
    val message: String? = null,
    val typeOfMessage: String? = null
)
