package aaa.app.android.sqlroomsample.domain.use_case

import aaa.app.android.sqlroomsample.domain.model.Expense
import aaa.app.android.sqlroomsample.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        repository.addExpense(expense)
    }
}
