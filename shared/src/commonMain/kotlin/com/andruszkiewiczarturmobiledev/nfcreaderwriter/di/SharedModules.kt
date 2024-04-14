package com.andruszkiewiczarturmobiledev.nfcreaderwriter.di

import org.koin.core.module.Module
import org.koin.dsl.module

private val localModule = module {
//    single<LocalUserRepository> { LocalUserRepositoryImpl(StoperekDatabase.invoke(get()), get()) }
//    single<LocalDepartmentRepository> { LocalDepartmentRepositoryImpl(StoperekDatabase.invoke(get()), get()) }
}


private val sharedModules = listOf(
    localModule
)

fun getSharedModules() = sharedModules

expect val platformModule: Module
