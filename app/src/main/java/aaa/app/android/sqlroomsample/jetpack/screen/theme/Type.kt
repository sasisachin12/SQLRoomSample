package aaa.app.android.sqlroomsample.jetpack.screen.theme

import aaa.app.android.sqlroomsample.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

private val fonts = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_medium, FontWeight.W500),
    Font(R.font.rubik_bold, FontWeight.Bold)
)

val typography = typographyFromDefaults(
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    h4 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    h5 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    h6 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 28.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 22.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    caption = TextStyle(
        fontFamily = fonts
    ),
    overline = TextStyle(
        letterSpacing = 0.08.em
    )
)

fun typographyFromDefaults(
    h1: TextStyle?,
    h2: TextStyle?,
    h3: TextStyle?,
    h4: TextStyle?,
    h5: TextStyle?,
    h6: TextStyle?,
    subtitle1: TextStyle?,
    subtitle2: TextStyle?,
    body1: TextStyle?,
    body2: TextStyle?,
    button: TextStyle?,
    caption: TextStyle?,
    overline: TextStyle?
): Typography {
    val defaults = Typography()
    return Typography(
        displayLarge = defaults.displayLarge.merge(h1),
        displayMedium = defaults.displayMedium.merge(h2),
        displaySmall = defaults.displaySmall.merge(h3),
        headlineLarge = defaults.headlineLarge.merge(h4),
        headlineMedium = defaults.headlineMedium.merge(h5),
        headlineSmall = defaults.headlineSmall.merge(h6),
        titleLarge = defaults.titleLarge.merge(subtitle1),
        titleMedium = defaults.titleMedium.merge(subtitle2),
        titleSmall = defaults.titleSmall.merge(body1),
        bodyLarge = defaults.bodyLarge.merge(body2),
        bodyMedium = defaults.bodyMedium.merge(button),
        bodySmall = defaults.bodySmall.merge(caption),
        labelLarge = defaults.labelLarge.merge(overline)
    )
}
