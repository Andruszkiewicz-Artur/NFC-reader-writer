package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation

import android.graphics.drawable.VectorDrawable

sealed class Screen(
    val route: String,
    val name: String?,
    val icon: VectorDrawable?
) {
    data object ReadPresentation : Screen(
        route = "ReadPresentation",
        name = "Read",
        icon = null
    )

    data object WritePresentation : Screen(
        route = "WritePresentation",
        name = "Write",
        icon = null
    )
}