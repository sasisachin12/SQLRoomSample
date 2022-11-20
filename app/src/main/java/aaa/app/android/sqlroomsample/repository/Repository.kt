package aaa.app.android.sqlroomsample.repository

import aaa.app.android.sqlroomsample.dao.ExpenseDao
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.convertDateToLong
import aaa.app.android.sqlroomsample.util.Utils.getCurrentMonthEnd
import aaa.app.android.sqlroomsample.util.Utils.getCurrentMonthStart
import androidx.lifecycle.LiveData
import java.text.SimpleDateFormat
import java.util.*

class Repository(private val expenseDao: ExpenseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.


    val startDate = convertDateToLong(getCurrentMonthStart(), DATE_FORMAT_ONE)
    val endDate = convertDateToLong(getCurrentMonthEnd(), DATE_FORMAT_ONE)

    val allExpenses: LiveData<List<ExpenseInfo>> =
        expenseDao.getAllExpense(startDate, endDate)

    suspend fun insert(expenseInfo: ExpenseInfo): Long {
        return expenseDao.insert(expenseInfo)
    }


    suspend fun getAll(): List<ExpenseInfo> {
        return expenseDao.getExpense()
    }


    suspend fun getDeleteById(id: Int) {
        expenseDao.deleteByID(id)
    }
}