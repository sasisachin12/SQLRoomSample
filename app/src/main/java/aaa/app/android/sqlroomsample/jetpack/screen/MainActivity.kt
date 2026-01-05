package aaa.app.android.sqlroomsample.jetpack.screen

import aaa.app.android.sqlroomsample.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val immersiveColor = ContextCompat.getColor(this, R.color.immersive_sys_ui)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(immersiveColor),
            navigationBarStyle = SystemBarStyle.dark(immersiveColor)
        )
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseApp { finish() }
        }
    }
}
