package aaa.app.android.sqlroomsample.dao

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Query("SELECT * FROM expense_table WHERE id = :taskId")
    fun observeById(taskId: String): Flow<ExpenseInfo>


    @Query("SELECT * FROM expense_table")
    suspend fun getAll(): List<ExpenseInfo>


    @Query("SELECT * FROM expense_table WHERE id = :taskId")
    suspend fun getById(taskId: String): ExpenseInfo?


    @Upsert
    suspend fun upsert(task: ExpenseInfo)


    @Upsert
    suspend fun upsertAll(tasks: List<ExpenseInfo>)


    @Query("UPDATE expense_table SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)


    @Query("DELETE FROM expense_table WHERE id = :taskId")
    suspend fun deleteById(taskId: String): Int


    @Query("DELETE FROM expense_table WHERE isCompleted = 1")
    suspend fun deleteCompleted(): Int

}
