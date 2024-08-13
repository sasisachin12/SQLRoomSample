package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.jetpack.screen.theme.BlueTheme
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.convertDateToLong
import aaa.app.android.sqlroomsample.util.Utils.convertMillisToDate
import aaa.app.android.sqlroomsample.util.Utils.formattedTime
import aaa.app.android.sqlroomsample.viewmodel.TasksViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
            AddMyExpense(
                viewModel::updateExpense,
                viewModel::updateExpenseAmount,
                { System.currentTimeMillis() },
                viewModel::updateExpenseDate,
                viewModel::createNewTask
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMyExpense(
    onExpenseDescription: (String) -> Unit,
    expenseAmount: (String) -> Unit,
    onDateSelected: (Long?) -> Unit,
    updateExpenseDate: (Long) -> Unit,
    onClick: () -> Unit
) {
    var expenseFor by remember { mutableStateOf("") }
    var expense by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    var showDatePicker by remember {
        mutableStateOf(false)
    }


    val currentTime = Calendar.getInstance()
    var showTime by remember {
        mutableStateOf(false)
    }
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )
    Column {

        OutlinedTextField(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            value = expenseFor,
            onValueChange = {
                onExpenseDescription(it)
                expenseFor = it
            },
            label = { Text("Expense") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            value = expense,
            onValueChange = {
                expenseAmount(it)
                expense = it
            },
            label = { Text("Expense Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {
            val date: Long = convertDateToLong(
                selectedDate + " " + formattedTime(timePickerState.hour, timePickerState.minute),
                "$DATE_FORMAT_ONE $TIME_FORMAT_ONE"
            )
            updateExpenseDate(date)
        },
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



    OutlinedTextField(
        value =
        formattedTime(timePickerState.hour, timePickerState.minute),
        onValueChange = {
            val date: Long = convertDateToLong(
                "$selectedDate $it",
                "$DATE_FORMAT_ONE $TIME_FORMAT_ONE"
            )
            updateExpenseDate(date)

        },
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
    Button(
        onClick = {
            expenseFor = ""
            expense = ""
            onClick()
        },
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


@Composable
fun PasswordValidationSample() {
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val isNotMatching = remember {
        derivedStateOf {
            password.isBlank()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        OutlinedTextField(value = password, onValueChange = { password = it })
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it })
        if (isNotMatching.value) {
            Text(text = "Passwords blank")
        }
    }
}




