package net.thesparrows.peter.phonetrack.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import net.thesparrows.peter.phonetrack.HomeState

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)


enum class Theme {
    DEFAULT,
    DARK_THEME,
    LIGHT_THEME,
    FOLLOW_DEVICE_THEME,
    DYNAMIC_LIGHT_THEME,
    DYNAMIC_DARK_THEME,
    DYNAMIC_THEME,
}

@Composable
fun PhoneTrackTheme(
    state: HomeState,
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    // Dynamic color is available on Android 12+
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colors = when (state.theme) {
        Theme.DARK_THEME -> darkScheme
        Theme.LIGHT_THEME -> lightScheme
        Theme.DYNAMIC_THEME -> {
            when {
                dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
                dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
                darkTheme -> darkScheme
                else -> lightScheme
            }
        }
        Theme.FOLLOW_DEVICE_THEME -> {
            when {
                darkTheme -> darkScheme
                else -> lightScheme
            }
        }
        Theme.DYNAMIC_LIGHT_THEME -> {
            when {
                dynamicColor -> dynamicLightColorScheme(LocalContext.current)
                else -> lightScheme
            }
        }
        Theme.DYNAMIC_DARK_THEME -> {
            when {
                dynamicColor -> dynamicDarkColorScheme(LocalContext.current)
                else -> darkScheme
            }
        }
        Theme.DEFAULT -> lightScheme
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

@Composable
fun textFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    cursorColor = MaterialTheme.colorScheme.primary,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
    focusedTextColor = MaterialTheme.colorScheme.secondary,
    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
)