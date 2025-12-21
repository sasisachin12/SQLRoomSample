package aaa.app.android.sqlroomsample.viewmodel

import aaa.app.android.sqlroomsample.domain.model.Expense
import aaa.app.android.sqlroomsample.domain.use_case.AddExpenseUseCase
import aaa.app.android.sqlroomsample.domain.use_case.DeleteExpenseUseCase
import aaa.app.android.sqlroomsample.domain.use_case.GetExpensesUseCase
import aaa.app.android.sqlroomsample.util.APPConstant
import aaa.app.android.sqlroomsample.util.Utils
import aaa.app.android.sqlroomsample.viewmodel.MyModelUiState.Loading
import aaa.app.android.sqlroomsample.viewmodel.MyModelUiState.Success
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
    val date: Long = Utils.convertDateToLong(
        Utils.getCurrentDate() + " " + Utils.getCurrentTime(),
        "${APPConstant.DATE_FORMAT_ONE} ${APPConstant.TIME_FORMAT_ONE}"
    ),
    val expense: String = "food",
    val amount: String = "0",
    var isCompleted: Boolean = false
)

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    private val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    val expenseList: StateFlow<MyModelUiState> = getExpensesUseCase()
        .map<List<Expense>, MyModelUiState> { Success(data = it) }
        .catch { emit(MyModelUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

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

    fun addExpense() = viewModelScope.launch {
        try {
            val expense = Expense(
                id = null,
                date = uiState.value.date,
                expense = uiState.value.expense,
                amount = uiState.value.amount
            )
            addExpenseUseCase(expense)
        } catch (e: Exception) {
            Log.e("createNewTask: ", e.message.toString())
        }
    }

    fun deleteRecord(expense: Expense) {
        try {
            viewModelScope.launch {
                deleteExpenseUseCase(expense)
            }
        } catch (e: Exception) {
            Log.e("deleteRecord: ", e.message.toString())
        }
    }
}

sealed interface MyModelUiState {
    object Loading : MyModelUiState
    data class Error(val throwable: Throwable) : MyModelUiState
    data class Success(val data: List<Expense>) : MyModelUiState
}
