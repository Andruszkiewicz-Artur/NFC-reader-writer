package com.andruszkiewiczarturmobiledev.nfcreaderwriter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform