package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.util.Utils.convertLongToTime
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ExpenseListScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        ExpenseAppBar()
        ExpenseList()
    }


}

@Composable
fun ExpenseList(viewModel: ExpenseViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        viewModel.getAllExpense()
    }
    val list = viewModel.expenseList.collectAsState().value
    list?.let {
        LazyColumn {

            items(items = list,
                itemContent = {
                    ExpenseItemRow(it, { viewModel::deleteRecord })
                })


        }
    }


}

@Composable

fun ExpenseItemRow(expenseInfoItem: ExpenseInfo, deleteClick: (ExpenseInfo) -> Unit) {
    val modifier = Modifier
        .padding(2.dp)
        .padding(2.dp)


    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = convertLongToTime(expenseInfoItem.date),
            modifier = modifier.weight(1f),
            color = Color.Gray
        )
        Text(text = expenseInfoItem.expense, modifier = modifier.weight(0.5f), color = Color.Black)
        Text(
            text = expenseInfoItem.amount,
            modifier = modifier
                .weight(0.5f)
                .fillMaxWidth(),
            color = Color.Magenta
        )
        IconButton(onClick = {
            deleteClick(expenseInfoItem)
        }, modifier = modifier.weight(0.5f)) {
            Icon(
                Icons.Rounded.Delete,
                contentDescription = stringResource(id = R.string.delete)
            )
        }

    }

}

