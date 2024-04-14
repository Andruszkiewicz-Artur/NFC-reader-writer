package com.andruszkiewiczarturmobiledev.nfcreaderwriter

import android.app.Application
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.di.getAppModule
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.di.initKoin
import org.koin.android.ext.koin.androidContext

class NFCApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(androidContext = this@NFCApp)

            modules(getAppModule())
        }
    }

}