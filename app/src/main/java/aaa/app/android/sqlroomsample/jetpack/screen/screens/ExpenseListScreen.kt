package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ExpenseListScreen() {

    LazyColumn(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {

        item {
            ExpenseAppBar()
        }
        item {
            ExpenseList()
        }


    }
}

@Composable
fun ExpenseList(viewModel: ExpenseViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        viewModel.getAllExpense()
    }
    val list = viewModel.expenseList.value as List<ExpenseInfo>
    LazyColumn() {

        items(items = list,
            itemContent = { ExpenseItemRow(it) })

    }

}

@Composable
fun ExpenseItemRow(it: ExpenseInfo) {


}

