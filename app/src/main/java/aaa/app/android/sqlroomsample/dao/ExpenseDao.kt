package aaa.app.android.sqlroomsample.dao

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ExpenseDao {


    @Query("Select * from expense_table ORDER BY id ASC")
    fun getAllExpense(): LiveData<List<ExpenseInfo>>


    @Query("Select * from expense_table ORDER BY id ASC")
    fun getExpense(): List<ExpenseInfo>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun  insert(expenseInfo: ExpenseInfo):Long

    @Query("DELETE FROM expense_table")
     fun deleteAll()


    @Query("DELETE FROM expense_table where id=:id")
    fun deleteByID(id:Int)


}