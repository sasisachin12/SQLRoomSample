package aaa.app.android.sqlroomsample.dao

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ExpenseDao {


    @Query("SELECT * FROM expense_table WHERE date BETWEEN :from AND :to")
    fun getAllExpense(from: Long, to: Long): LiveData<List<ExpenseInfo>>

    /*@Query("Select * from expense_table ORDER BY id DESC")
    fun getAllExpense(): LiveData<List<ExpenseInfo>>
*/

    @Query("Select * from expense_table ORDER BY id DESC")
    fun getExpense(): List<ExpenseInfo>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insert(expenseInfo: ExpenseInfo):Long

    @Query("DELETE FROM expense_table")
     fun deleteAll()


    @Query("DELETE FROM expense_table where id=:id")
    fun deleteByID(id:Int)


}