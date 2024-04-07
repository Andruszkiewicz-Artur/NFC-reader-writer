package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.domain.service

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.Static
import java.util.Arrays

/**
 * Created by Qifan on 05/12/2018.
 */

class MyHostApduService : HostApduService() {

    companion object {
        private const val TAG = "mHostApduService_TAG"

        private var mAppSelected = false
        private var mCcSelected = false
        private var mNdefSelected = false
    }

    private var mNdefRecordFile: ByteArray = byteArrayOf()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { innerIntent ->
            if (innerIntent.hasExtra("ndefMessage")) {
                val ndefMessage: NdefMessage? = getNdefMessage(innerIntent.getStringExtra("ndefMessage") ?: "")


                if (ndefMessage != null) {
                    val nlen = ndefMessage.byteArrayLength
                    mNdefRecordFile = ByteArray(nlen + 2)
                    mNdefRecordFile[0] = ((nlen and 0xff00) / 256).toByte()
                    mNdefRecordFile[1] = (nlen and 0xff).toByte()
                    System.arraycopy(
                        ndefMessage.toByteArray(),
                        0,
                        mNdefRecordFile,
                        2,
                        ndefMessage.byteArrayLength
                    )
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {
        if (Arrays.equals(Static.SELECT_APPLICATION, commandApdu)) {
            mAppSelected = true
            mCcSelected = false
            mNdefSelected = false
            Log.d(TAG, "responseApdu: " + bytesToHex(Static.SUCCESS_SW) + " || Success_SW")
            return Static.SUCCESS_SW
        } else if (mAppSelected && Arrays.equals(
                Static.SELECT_CAPABILITY_CONTAINER,
                commandApdu
            )
        ) {
            mCcSelected = true
            mNdefSelected = false
            Log.d(TAG, "responseApdu: " + bytesToHex(Static.SUCCESS_SW) + " || Success_SW")
            return Static.SUCCESS_SW
        } else if (mAppSelected && Arrays.equals(Static.SELECT_NDEF_FILE, commandApdu)) {
            // NDEF
            mCcSelected = false
            mNdefSelected = true
            Log.d(TAG, "responseApdu: " + bytesToHex(Static.SUCCESS_SW) + " || Success_SW")
            return Static.SUCCESS_SW
        } else if (commandApdu[0] == 0x00.toByte() && commandApdu[1] == 0xb0.toByte()) {
            val offset =
                (0x00ff and commandApdu[2].toInt()) * 256 + (0x00ff and commandApdu[3].toInt())
            val le = 0x00ff and commandApdu[4].toInt()
            val responseApdu = ByteArray(le + Static.SUCCESS_SW.size)
            if (mCcSelected && offset == 0 && le == Static.CAPABILITY_CONTAINER_FILE.size) {
                System.arraycopy(
                    Static.CAPABILITY_CONTAINER_FILE,
                    offset,
                    responseApdu,
                    0,
                    le
                )
                System.arraycopy(
                    Static.SUCCESS_SW,
                    0,
                    responseApdu,
                    le,
                    Static.SUCCESS_SW.size
                )
                Log.d(TAG, "responseApdu: " + bytesToHex(responseApdu) + " || Success_SW")
                return responseApdu
            } else if (mNdefSelected) {
                if (offset + le <= mNdefRecordFile.size) {
                    System.arraycopy(mNdefRecordFile, offset, responseApdu, 0, le)
                    System.arraycopy(
                        Static.SUCCESS_SW,
                        0,
                        responseApdu,
                        le,
                        Static.SUCCESS_SW.size
                    )
                    Log.d(TAG, "responseApdu: " + bytesToHex(responseApdu) + " || Success_SW")
                    return responseApdu
                }
            }
        }
        Log.d(TAG, "responseApdu: " + bytesToHex(Static.FAILURE_SW) + " || FAILURE_SW")
        return Static.FAILURE_SW
    }

    override fun onDeactivated(reason: Int) {
        mAppSelected = false
        mCcSelected = false
        mNdefSelected = false
    }

    private fun getNdefMessage(ndefData: String): NdefMessage? {
        if (ndefData.isEmpty()) {
            return null
        }
        val ndefRecord: NdefRecord = NdefRecord.createTextRecord("en", ndefData)
        return NdefMessage(ndefRecord)
    }

    private fun bytesToHex(bytes: ByteArray): String? {
        val result = StringBuffer()
        for (b in bytes) result.append(
            Integer.toString((b.toInt() and 0xff) + 0x100, 16).substring(1)
        )
        return result.toString()
    }
}