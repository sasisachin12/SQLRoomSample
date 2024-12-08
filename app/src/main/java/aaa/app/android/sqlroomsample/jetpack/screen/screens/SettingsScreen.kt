package aaa.app.android.sqlroomsample.jetpack.screen.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val (searchTerm, updateSearchTerm) = remember { mutableStateOf(TextFieldValue("")) }
    LazyColumn(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxHeight()
    ) {
        item { AppBar(searchTerm, updateSearchTerm) }


    }
}


@Composable
private fun AppBar(
    searchTerm: TextFieldValue,
    updateSearchTerm: (TextFieldValue) -> Unit
) {

}


