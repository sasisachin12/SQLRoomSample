package aaa.app.android.sqlroomsample.jetpack.screen.theme

import aaa.app.android.sqlroomsample.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val YellowThemeLight = lightColorScheme(
    primary = yellow500,
    onPrimary = Color.Black,
    secondary = yellow200,
    onSecondary = Color.White
)

private val YellowThemeDark = darkColorScheme(
    primary = yellow200,
    secondary = yellow200,
    onSecondary = Color.Black,
    surface = yellowDarkPrimary
)

@Composable
fun YellowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        YellowThemeDark
    } else {
        YellowThemeLight
    }
    OwlTheme(darkTheme, colors, content)
}


private val LightElevation = Elevations()

private val DarkElevation = Elevations(card = 1.dp)

private val LightImages = Images(lockupLogo = R.drawable.ic_lockup_blue)

private val DarkImages = Images(lockupLogo = R.drawable.ic_lockup_blue)

@Composable
private fun OwlTheme(
    darkTheme: Boolean,
    colors: ColorScheme,
    content: @Composable () -> Unit
) {
    val elevation = if (darkTheme) DarkElevation else LightElevation
    val images = if (darkTheme) DarkImages else LightImages
    CompositionLocalProvider(
        LocalElevations provides elevation,
        LocalImages provides images
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}


