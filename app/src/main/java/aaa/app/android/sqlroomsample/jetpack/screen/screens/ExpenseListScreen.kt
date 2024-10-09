package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.jetpack.screen.theme.rowBackGround
import aaa.app.android.sqlroomsample.util.Utils.convertLongToTime
import aaa.app.android.sqlroomsample.util.Utils.numberToRupees
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import aaa.app.android.sqlroomsample.viewmodel.MyModelUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


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
    /* LaunchedEffect(true) {
         viewModel.getAllExpense()
     }*/
    //val list = viewModel.expenseList.collectAsState()
    val items by viewModel.uiStateNew.collectAsStateWithLifecycle()
    if (items is MyModelUiState.Success) {
        LazyColumn {
            items(items = (items as MyModelUiState.Success).data,
                itemContent = {
                    ExpenseItemRow(it, viewModel::deleteRecord)
                })

        }
    }


}

@Composable
fun ExpenseItemRow(expenseInfoItem: ExpenseInfo, deleteClick: (ExpenseInfo) -> Unit) {
    val modifier = Modifier
        .padding(2.dp)
        .padding(2.dp)

    Text(
        text = convertLongToTime(expenseInfoItem.date),
        modifier = modifier,
        color = Color.Gray,

        )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = rowBackGround),

        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var isClicked by remember { mutableStateOf(false) }

        IconButton(onClick = {
            isClicked = true

        }, modifier = modifier.weight(0.5f)) {
            Icon(
                Icons.Rounded.Delete,
                contentDescription = stringResource(id = R.string.delete)
            )
        }

        if (isClicked) {
            ConfirmDialog(
                title = "Delete",
                content = "do you want delete?",
                { deleteClick(expenseInfoItem) },
                { isClicked = false })
        }


        Text(
            text = expenseInfoItem.expense,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
                .weight(2f)
                .align(Alignment.CenterVertically),
            color = Color.Black
        )
        Text(
            text = numberToRupees(expenseInfoItem.amount.toInt()),

            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.End,
            color = Color.Black
        )


    }


}

@Composable
fun ConfirmDialog(
    title: String? = null,
    content: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = {
            onDismiss()
        },

        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {

                if (!title.isNullOrEmpty()) {
                    Text(
                        title,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Text(content)

            }
        },

        dismissButton = {
            Button(onClick = {
                onDismiss()

            }) {
                Text("No")
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("Yes")
            }
        }
    )
}

