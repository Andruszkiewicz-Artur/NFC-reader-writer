package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateViewModel
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.write.WriteViewModel

private val viewModelModule = module {
    singleOf(::EmulateViewModel)
    singleOf(::WriteViewModel)
}

private val appModule = listOf(
    viewModelModule
)

fun getAppModule() = appModule