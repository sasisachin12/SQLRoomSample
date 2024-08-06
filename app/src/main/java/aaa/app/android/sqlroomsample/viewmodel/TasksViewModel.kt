package aaa.app.android.sqlroomsample.viewmodel

import aaa.app.android.sqlroomsample.data.TaskRepository
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.convertDateToLong
import aaa.app.android.sqlroomsample.util.Utils.getCurrentDate
import aaa.app.android.sqlroomsample.util.Utils.getCurrentTime
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AddExpenseUiState(
    val date: Long = convertDateToLong(
        getCurrentDate() + " " + getCurrentTime(),
        "$DATE_FORMAT_ONE $TIME_FORMAT_ONE"
    ),
    val expense: String = "",
    val amount: String = "0",
    var isCompleted: Boolean = false
)


@HiltViewModel
class TasksViewModel @Inject constructor(
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

    fun createNewTask() = viewModelScope.launch {
        taskRepository.createTask(
            uiState.value.date,
            uiState.value.expense,
            uiState.value.amount,
            uiState.value.isCompleted
        )

    }
}



