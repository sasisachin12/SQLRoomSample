package aaa.app.android.sqlroomsample.viewmodel

import aaa.app.android.sqlroomsample.data.TaskRepository
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.convertDateToLong
import aaa.app.android.sqlroomsample.util.Utils.getCurrentDate
import aaa.app.android.sqlroomsample.util.Utils.getCurrentTime
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AddExpenseUiState(
    val date: Long = convertDateToLong(
        getCurrentDate() + " " + getCurrentTime(),
        "$DATE_FORMAT_ONE $TIME_FORMAT_ONE"
    ),
    val expense: String = "food",
    val amount: String = "0",
    var isCompleted: Boolean = false
)


@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()


    fun clearCompletedTasks() {
        viewModelScope.launch {
            taskRepository.clearCompletedTasks()

        }
    }

    fun updateExpense(expense: String) {
        _uiState.update {
            it.copy(expense = expense)
        }
    }

    fun updateExpenseAmount(expenseAmount: String) {
        _uiState.update {
            it.copy(amount = expenseAmount)
        }
    }

    fun updateExpenseDate(date: Long) {
        _uiState.update {
            it.copy(date = date)
        }
    }

    fun createNewTask() = viewModelScope.launch {

        try {
            taskRepository.createTask(
                uiState.value.date,
                uiState.value.expense,
                uiState.value.amount,
                uiState.value.isCompleted
            )
        } catch (e: Exception) {
            val s = e.message
            Log.e("createNewTask: ", e.message.toString())
        }

    }
}



