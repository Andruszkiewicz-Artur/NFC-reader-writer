package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.presentation.utils.navigation

import android.graphics.drawable.VectorDrawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val name: String?,
    val icon: ImageVector?
) {
    data object ReadPresentation : Screen(
        route = "ReadPresentation",
        name = "Read",
        icon = Icons.Outlined.LocalLibrary
    )

    data object WritePresentation : Screen(
        route = "WritePresentation",
        name = "Write",
        icon = Icons.Outlined.EditNote
    )

    data object EmulatePresentation : Screen(
        route = "EmulatePresentation",
        name = "Emulate",
        icon = Icons.Outlined.CreditCard
    )
}