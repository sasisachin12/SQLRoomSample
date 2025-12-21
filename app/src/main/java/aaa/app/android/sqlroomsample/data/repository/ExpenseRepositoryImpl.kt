package aaa.app.android.sqlroomsample.data.repository

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.data.local.entity.toExpense
import aaa.app.android.sqlroomsample.data.local.entity.toExpenseEntity
import aaa.app.android.sqlroomsample.domain.model.Expense
import aaa.app.android.sqlroomsample.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : ExpenseRepository {

    override fun getExpenses(): Flow<List<Expense>> {
        return dao.getExpenses().map { it.map { entity -> entity.toExpense() } }
    }

    override suspend fun addExpense(expense: Expense) {
        dao.insertExpense(expense.toExpenseEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toExpenseEntity())
    }
}
