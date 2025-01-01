package aaa.app.android.sqlroomsample.data

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    val expenseList: Flow<List<ExpenseInfo>>


    suspend fun addExpense(
        expenseInfo: ExpenseInfo
    ): String


    suspend fun deleteTask(taskId: ExpenseInfo)
}
