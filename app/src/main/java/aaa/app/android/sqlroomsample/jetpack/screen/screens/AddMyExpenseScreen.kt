package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.jetpack.screen.theme.BlueTheme
import aaa.app.android.sqlroomsample.util.Utils.convertMillisToDate
import aaa.app.android.sqlroomsample.viewmodel.TasksViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMyExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel()
) {
    LazyColumn(

        modifier = modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        item {
            CoursesAppBar()
            ExpenseFiled()
            ExpenseAmountFiled()
            // DatePickerDocked()
            DatePickerModal { System.currentTimeMillis() }
            val currentTime = Calendar.getInstance()

            DialWithDialogExample({
                TimePickerState(
                    initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
                    initialMinute = currentTime.get(Calendar.MINUTE),
                    is24Hour = true,
                )
            })
            SaveExpenseButton(onClick = viewModel::createNewTask)
        }

    }
}

@Composable
fun ExpenseFiled() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Expense") }
    )
}

@Composable
fun ExpenseAmountFiled() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Expense Amount") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun SaveExpenseButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(12.dp)
    ) {
        Text("Save")
    }
}


@Preview(name = "My Courses")
@Composable
private fun MyCoursesPreview() {
    BlueTheme {
        AddMyExpenseScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit
) {

    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = selectedDate,
        onValueChange = { },
        label = { Text(text = "Date") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = !showDatePicker }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    )


    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialogExample(
    onConfirm: (TimePickerState) -> Unit,

    ) {
    val currentTime = Calendar.getInstance()
    var showTime by remember {
        mutableStateOf(false)
    }
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    OutlinedTextField(
        value = timePickerState.hour.toString() + " : " + timePickerState.minute.toString(),
        onValueChange = { },
        label = { Text(text = "Time") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showTime = !showTime }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    )
    if (showTime)
        TimePickerDialog(
            onDismiss = { showTime = false },
            onConfirm = { showTime = false }
        ) {
            TimePicker(
                state = timePickerState,
            )
        }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}




