package aaa.app.android.sqlroomsample.domain.repository

import aaa.app.android.sqlroomsample.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    fun getExpenses(): Flow<List<Expense>>

    suspend fun addExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}
