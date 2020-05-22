package aaa.app.android.sqlroomsample.repository

import aaa.app.android.sqlroomsample.dao.ExpenseDao
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.lifecycle.LiveData

class Repository(private val expenseDao: ExpenseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allExpenses: LiveData<List<ExpenseInfo>> = expenseDao.getAllExpense()

    suspend fun insert(expenseInfo: ExpenseInfo): Long {
        return expenseDao.insert(expenseInfo)
    }


    suspend fun getAll(): List<ExpenseInfo> {
        return expenseDao.getExpense()
    }


    suspend fun getDeleteById(id:Int) {
         expenseDao.deleteByID(id)
    }
}