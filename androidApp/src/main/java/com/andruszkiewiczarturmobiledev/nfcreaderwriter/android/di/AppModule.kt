package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.emulate.EmulateViewModel

private val viewModelModule = module {
    singleOf(::EmulateViewModel)
}

private val appModule = listOf(
    viewModelModule
)

fun getAppModule() = appModule