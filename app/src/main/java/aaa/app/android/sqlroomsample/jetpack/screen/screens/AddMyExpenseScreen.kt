package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.jetpack.screen.theme.YellowTheme
import aaa.app.android.sqlroomsample.util.Utils.convertMillisToDate
import aaa.app.android.sqlroomsample.util.Utils.formattedTime
import aaa.app.android.sqlroomsample.util.Utils.numberToRupees
import aaa.app.android.sqlroomsample.viewmodel.AddExpenseUiState
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import aaa.app.android.sqlroomsample.viewmodel.MyModelUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddMyExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val expenseListState by viewModel.expenseList.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            // Launching a separate job to show the snackbar indefinitely and then dismiss it after 30 seconds
            val job = launch {
                snackbarHostState.showSnackbar(
                    message = "Expense added successfully",
                    duration = SnackbarDuration.Indefinite
                )
            }
            delay(30000) // 30 seconds delay
            job.cancel() // Dismiss the snackbar
            viewModel.resetCompleted()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AddMyExpenseHeader()

        if (expenseListState is MyModelUiState.Success) {
            val data = (expenseListState as MyModelUiState.Success).data
            
            val totalOnSelectedDate = data
                .filter { convertMillisToDate(it.date) == convertMillisToDate(uiState.date) }
                .sumOf { it.amount.toIntOrNull() ?: 0 }

            val selectedCalendar = Calendar.getInstance().apply { timeInMillis = uiState.date }
            val totalInSelectedMonth = data
                .filter {
                    val expenseCalendar = Calendar.getInstance().apply { timeInMillis = it.date }
                    expenseCalendar.get(Calendar.MONTH) == selectedCalendar.get(Calendar.MONTH) &&
                            expenseCalendar.get(Calendar.YEAR) == selectedCalendar.get(Calendar.YEAR)
                }
                .sumOf { it.amount.toIntOrNull() ?: 0 }

            val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date(uiState.date))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    DailyTotalSection(totalOnSelectedDate, convertMillisToDate(uiState.date))
                }
                Box(modifier = Modifier.weight(1f)) {
                    MonthlyTotalSection(totalInSelectedMonth, monthName)
                }
            }
        }

        AddMyExpense(
            uiState = uiState,
            onExpenseDescription = viewModel::updateExpense,
            onExpenseAmount = viewModel::updateExpenseAmount,
            onDateUpdate = viewModel::updateExpenseDate,
            onSaveClick = viewModel::addExpense,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AddMyExpenseHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Add New Expense",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Track your spending effortlessly",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DailyTotalSection(total: Int, date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Payments,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Spent on $date",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
            Text(
                text = numberToRupees(total),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun MonthlyTotalSection(total: Int, month: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Total in $month",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1
            )
            Text(
                text = numberToRupees(total),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMyExpense(
    uiState: AddExpenseUiState,
    onExpenseDescription: (String) -> Unit,
    onExpenseAmount: (String) -> Unit,
    onDateUpdate: (Long) -> Unit,
    onSaveClick: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance().apply { timeInMillis = uiState.date }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.expense,
                onValueChange = {
                    onExpenseDescription(it)
                    showError = false
                },
                label = { Text("What did you spend on?") },
                placeholder = { Text("e.g. Grocery, Coffee") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                singleLine = true,
                isError = showError && uiState.expense.isBlank(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.amount,
                onValueChange = { input ->
                    // Allow numeric characters and a single decimal point
                    if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        onExpenseAmount(input)
                        showError = false
                    }
                },
                label = { Text("How much?") },
                placeholder = { Text("0.00") },
                leadingIcon = { Icon(Icons.Default.Payments, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                isError = showError && uiState.amount.isBlank(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            if (showError && (uiState.expense.isBlank() || uiState.amount.isBlank())) {
                Text(
                    text = "Please enter both description and amount",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Column {
                            Text("Date", style = MaterialTheme.typography.labelSmall)
                            Text(convertMillisToDate(uiState.date), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Card(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Column {
                            Text("Time", style = MaterialTheme.typography.labelSmall)
                            Text(
                                formattedTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (uiState.expense.isNotBlank() && uiState.amount.isNotBlank()) {
                        onSaveClick()
                        showError = false
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    "Save Expense",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = uiState.date
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis ?: uiState.date
                    val newCalendar = Calendar.getInstance().apply { timeInMillis = selectedMillis }
                    val currentCalendar = Calendar.getInstance().apply { timeInMillis = uiState.date }

                    newCalendar.set(Calendar.HOUR_OF_DAY, currentCalendar.get(Calendar.HOUR_OF_DAY))
                    newCalendar.set(Calendar.MINUTE, currentCalendar.get(Calendar.MINUTE))

                    onDateUpdate(newCalendar.timeInMillis)
                    showDatePicker = false
                }) {
                    Text("Select")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = calendar.get(Calendar.HOUR_OF_DAY),
            initialMinute = calendar.get(Calendar.MINUTE),
            is24Hour = false
        )
        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = {
                val newCalendar = Calendar.getInstance().apply { timeInMillis = uiState.date }
                newCalendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                newCalendar.set(Calendar.MINUTE, timePickerState.minute)

                onDateUpdate(newCalendar.timeInMillis)
                showTimePicker = false
            },
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddMyExpensePreview() {
    YellowTheme {
        AddMyExpense(
            uiState = AddExpenseUiState(),
            onExpenseDescription = {},
            onExpenseAmount = {},
            onDateUpdate = {},
            onSaveClick = {}
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Set Time")
            }
        },
        text = { content() },
    )
}
