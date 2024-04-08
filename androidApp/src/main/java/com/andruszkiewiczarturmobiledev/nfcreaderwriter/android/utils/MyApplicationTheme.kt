package com.andruszkiewiczarturmobiledev.nfcreaderwriter.android.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3)
        )
    }
    val customColors = if (darkTheme) {
        darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )
    } else {
        lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )
    }

    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = customColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

val md_theme_light_primary = Color(0xFF6C5E00)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFCE350)
val md_theme_light_onPrimaryContainer = Color(0xFF211C00)
val md_theme_light_secondary = Color(0xFF626100)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFEAE86F)
val md_theme_light_onSecondaryContainer = Color(0xFF1D1D00)
val md_theme_light_tertiary = Color(0xFF006495)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFCBE6FF)
val md_theme_light_onTertiaryContainer = Color(0xFF001E30)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1D1C16)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1D1C16)
val md_theme_light_surfaceVariant = Color(0xFFE9E2D0)
val md_theme_light_onSurfaceVariant = Color(0xFF4A4739)
val md_theme_light_outline = Color(0xFF7B7768)
val md_theme_light_inverseOnSurface = Color(0xFFF5F0E7)
val md_theme_light_inverseSurface = Color(0xFF32302A)
val md_theme_light_inversePrimary = Color(0xFFDFC735)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF6C5E00)
val md_theme_light_outlineVariant = Color(0xFFCCC6B5)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFDFC735)
val md_theme_dark_onPrimary = Color(0xFF383000)
val md_theme_dark_primaryContainer = Color(0xFF514700)
val md_theme_dark_onPrimaryContainer = Color(0xFFFCE350)
val md_theme_dark_secondary = Color(0xFFCDCC56)
val md_theme_dark_onSecondary = Color(0xFF333200)
val md_theme_dark_secondaryContainer = Color(0xFF4A4900)
val md_theme_dark_onSecondaryContainer = Color(0xFFEAE86F)
val md_theme_dark_tertiary = Color(0xFF8FCDFF)
val md_theme_dark_onTertiary = Color(0xFF00344F)
val md_theme_dark_tertiaryContainer = Color(0xFF004B71)
val md_theme_dark_onTertiaryContainer = Color(0xFFCBE6FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1D1C16)
val md_theme_dark_onBackground = Color(0xFFE7E2D9)
val md_theme_dark_surface = Color(0xFF1D1C16)
val md_theme_dark_onSurface = Color(0xFFE7E2D9)
val md_theme_dark_surfaceVariant = Color(0xFF4A4739)
val md_theme_dark_onSurfaceVariant = Color(0xFFCCC6B5)
val md_theme_dark_outline = Color(0xFF969180)
val md_theme_dark_inverseOnSurface = Color(0xFF1D1C16)
val md_theme_dark_inverseSurface = Color(0xFFE7E2D9)
val md_theme_dark_inversePrimary = Color(0xFF6C5E00)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFDFC735)
val md_theme_dark_outlineVariant = Color(0xFF4A4739)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFFCCB521)
