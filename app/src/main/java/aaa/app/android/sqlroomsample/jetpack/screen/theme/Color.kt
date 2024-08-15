package aaa.app.android.sqlroomsample.jetpack.screen.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver


val yellow200 = Color(0xFFC5801A)
val yellow400 = Color(0xFFF57C00)
val yellow500 = Color(0xFFEF6C00)
val yellow800 = Color(0xFFEF6C00)
val yellowDarkPrimary = Color(0xFFFF6D00)


val yellowBackGround = Color(0xffF4C27F)


/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}
