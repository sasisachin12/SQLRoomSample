package aaa.app.android.sqlroomsample.data

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.data.local.entity.ExpenseEntity
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultExpenseRepository @Inject constructor(
    private val taskDao: TaskDao
) : ExpenseRepository {

    override val expenseList: Flow<List<ExpenseInfo>> = taskDao.getExpenses().map { list ->
        list.map { entity ->
            ExpenseInfo(
                id = entity.id,
                date = entity.date,
                expense = entity.expense,
                amount = entity.amount
            )
        }
    }

    override suspend fun addExpense(expense: ExpenseInfo) {
        taskDao.insertExpense(
            ExpenseEntity(
                id = expense.id,
                date = expense.date,
                expense = expense.expense,
                amount = expense.amount
            )
        )
    }

    override suspend fun deleteTask(expense: ExpenseInfo) {
        taskDao.deleteExpense(
            ExpenseEntity(
                id = expense.id,
                date = expense.date,
                expense = expense.expense,
                amount = expense.amount
            )
        )
    }
}
