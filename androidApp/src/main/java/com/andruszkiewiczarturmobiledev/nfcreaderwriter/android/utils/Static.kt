package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils

object Static {
    val SELECT_APPLICATION = byteArrayOf(
        0x00.toByte(),
        0xA4.toByte(),
        0x04.toByte(),
        0x00.toByte(),
        0x07.toByte(),
        0xD2.toByte(),
        0x76.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x85.toByte(),
        0x01.toByte(),
        0x01.toByte(),
        0x00.toByte()
    )

    val SELECT_CAPABILITY_CONTAINER = byteArrayOf(
        0x00.toByte(),
        0xa4.toByte(),
        0x00.toByte(),
        0x0c.toByte(),
        0x02.toByte(),
        0xe1.toByte(),
        0x03.toByte()
    )

    val SELECT_NDEF_FILE = byteArrayOf(
        0x00.toByte(),
        0xa4.toByte(),
        0x00.toByte(),
        0x0c.toByte(),
        0x02.toByte(),
        0xE1.toByte(),
        0x04.toByte()
    )

    val CAPABILITY_CONTAINER_FILE = byteArrayOf(
        0x00,
        0x0f,  // CCLEN
        0x20,  // Mapping Version
        0x00,
        0x3b,  // Maximum R-APDU data size
        0x00,
        0x34,  // Maximum C-APDU data size
        0x04,
        0x06,
        0xe1.toByte(),
        0x04,
        0x00.toByte(),
        0xff.toByte(),  // Maximum NDEF size, do NOT extend this value
        0x00,
        0xff.toByte()
    )

    val SUCCESS_SW = byteArrayOf(0x90.toByte(), 0x00.toByte())

    val FAILURE_SW = byteArrayOf(0x6a.toByte(), 0x82.toByte())
}