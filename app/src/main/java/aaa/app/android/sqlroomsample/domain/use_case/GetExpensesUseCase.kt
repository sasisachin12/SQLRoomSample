package aaa.app.android.sqlroomsample.domain.use_case

import aaa.app.android.sqlroomsample.domain.model.Expense
import aaa.app.android.sqlroomsample.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getExpenses()
    }
}
