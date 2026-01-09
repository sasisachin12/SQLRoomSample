package aaa.app.android.sqlroomsample.viewmodel

import aaa.app.android.sqlroomsample.domain.model.Expense
import aaa.app.android.sqlroomsample.domain.use_case.AddExpenseUseCase
import aaa.app.android.sqlroomsample.domain.use_case.DeleteExpenseUseCase
import aaa.app.android.sqlroomsample.domain.use_case.GetExpensesUseCase
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.convertDateToLong
import aaa.app.android.sqlroomsample.util.Utils.getCurrentDate
import aaa.app.android.sqlroomsample.util.Utils.getCurrentTime
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AddExpenseUiState(
    val date: Long = convertDateToLong(
        getCurrentDate() + " " + getCurrentTime(),
        "$DATE_FORMAT_ONE $TIME_FORMAT_ONE"
    ),
    val expense: String = "",
    val amount: String = "",
    val isCompleted: Boolean = false
)


@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()


    val expenseList: StateFlow<MyModelUiState> = getExpensesUseCase()
        .map<List<Expense>, MyModelUiState> { MyModelUiState.Success(data = it) }
        .catch { emit(MyModelUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyModelUiState.Loading)


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

    fun resetCompleted() {
        _uiState.update {
            it.copy(isCompleted = false)
        }
    }

    fun addExpense() = viewModelScope.launch {
        try {
            val expense = Expense(
                id = null,
                uiState.value.date,
                uiState.value.expense,
                uiState.value.amount
            )
            addExpenseUseCase(expense)
            // Show success message
            _uiState.update { 
                it.copy(
                    isCompleted = true,
                    expense = "",
                    amount = ""
                ) 
            }
        } catch (e: Exception) {
            Log.e("ExpenseViewModel", "Error saving expense: ${e.message}")
        }
    }



    fun deleteRecord(expense: Expense) {
        viewModelScope.launch {
            try {
                deleteExpenseUseCase(expense)
            } catch (e: Exception) {
                Log.e("deleteRecord: ", e.message.toString())
            }
        }
    }


}

sealed interface MyModelUiState {
    data object Loading : MyModelUiState
    data class Error(val throwable: Throwable) : MyModelUiState
    data class Success(val data: List<Expense>) : MyModelUiState
}
