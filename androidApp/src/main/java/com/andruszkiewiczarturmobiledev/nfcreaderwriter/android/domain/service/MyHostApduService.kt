package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.domain.service

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.Static
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils.Utils
import java.util.Arrays

/**
 * Created by Qifan on 05/12/2018.
 */

class MyHostApduService : HostApduService() {

    companion object {
        private const val TAG = "mHostApduService_TAG"

        // アプリが選択されているかどうかのフラグ
        private var mAppSelected = false

        // CCファイルが選択されているかどうかのフラグ
        private var mCcSelected = false

        // NDEFレコードファイルが選択されているかどうかのフラグ
        private var mNdefSelected = false
    }

    private var mNdefRecordFile: ByteArray = byteArrayOf()

    override fun onCreate() {
        super.onCreate()

        // default NDEF-message
        val DEFAULT_MESSAGE = "This is message from me!!!! yea im great!!!"
        val ndefDefaultMessage = getNdefMessage(DEFAULT_MESSAGE)

        // the maximum length is 246 so do not extend this value
        val nlen = ndefDefaultMessage!!.byteArrayLength
        mNdefRecordFile = ByteArray(nlen + 2)
        mNdefRecordFile[0] = ((nlen and 0xff00) / 256).toByte()
        mNdefRecordFile[1] = (nlen and 0xff).toByte()
        System.arraycopy(
            ndefDefaultMessage.toByteArray(),
            0,
            mNdefRecordFile,
            2,
            ndefDefaultMessage.byteArrayLength
        )
    }

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
            } else if (innerIntent.hasExtra("ndefUrl")) {
                val ndefMessage: NdefMessage? = getNdefUrlMessage(innerIntent.getStringExtra("ndefMessage") ?: "")

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
        Log.d(TAG, "commandApdu: " + Utils.bytesToHex(commandApdu))
        //if (Arrays.equals(SELECT_APP, commandApdu)) {
        // check if commandApdu qualifies for SELECT_APPLICATION
        //if (Arrays.equals(SELECT_APP, commandApdu)) {
        // check if commandApdu qualifies for SELECT_APPLICATION
        if (Arrays.equals(Static.SELECT_APPLICATION, commandApdu)) {
            mAppSelected = true
            mCcSelected = false
            mNdefSelected = false
            Log.d(TAG, "responseApdu: " + Utils.bytesToHex(Static.SUCCESS_SW))
            return Static.SUCCESS_SW
            // check if commandApdu qualifies for SELECT_CAPABILITY_CONTAINER
        } else if (mAppSelected && Arrays.equals(
                Static.SELECT_CAPABILITY_CONTAINER,
                commandApdu
            )
        ) {
            mCcSelected = true
            mNdefSelected = false
            Log.d(TAG, "responseApdu: " + Utils.bytesToHex(Static.SUCCESS_SW))
            return Static.SUCCESS_SW
            // check if commandApdu qualifies for SELECT_NDEF_FILE
        } else if (mAppSelected && Arrays.equals(Static.SELECT_NDEF_FILE, commandApdu)) {
            // NDEF
            mCcSelected = false
            mNdefSelected = true
            Log.d(TAG, "responseApdu: " + Utils.bytesToHex(Static.SUCCESS_SW))
            return Static.SUCCESS_SW
            // check if commandApdu qualifies for // READ_BINARY
        } else if (commandApdu[0] == 0x00.toByte() && commandApdu[1] == 0xb0.toByte()) {
            // READ_BINARY
            // get the offset an le (length) data
            //System.out.println("** " + Utils.bytesToHex(commandApdu) + " in else if (commandApdu[0] == (byte)0x00 && commandApdu[1] == (byte)0xb0) {");
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
                Log.d(TAG, "responseApdu: " + Utils.bytesToHex(responseApdu))
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
                    Log.d(TAG, "responseApdu: " + Utils.bytesToHex(responseApdu))
                    return responseApdu
                }
            }
        }

        // The tag should return different errors for different reasons
        // this emulation just returns the general error message

        // The tag should return different errors for different reasons
        // this emulation just returns the general error message
        Log.d(TAG, "responseApdu: " + Utils.bytesToHex(Static.FAILURE_SW))
        return Static.FAILURE_SW
    }

    override fun onDeactivated(reason: Int) {

    }

    private fun getNdefMessage(ndefData: String): NdefMessage? {
        if (ndefData.isEmpty()) {
            return null
        }
        val ndefRecord: NdefRecord = NdefRecord.createTextRecord("en", ndefData)
        return NdefMessage(ndefRecord)
    }

    private fun getNdefUrlMessage(ndefData: String): NdefMessage? {
        if (ndefData.isEmpty()) {
            return null
        }
        val ndefRecord: NdefRecord = NdefRecord.createUri(ndefData)
        return NdefMessage(ndefRecord)
    }
}