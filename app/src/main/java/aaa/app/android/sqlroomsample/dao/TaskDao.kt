package aaa.app.android.sqlroomsample.dao

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM expense_table WHERE id = :taskId")
    fun observeById(taskId: String): Flow<ExpenseInfo>

    @Query("SELECT * FROM expense_table")
    fun getAll(): Flow<List<ExpenseInfo>>

    @Query("SELECT * FROM expense_table WHERE id = :taskId")
    suspend fun getById(taskId: String): ExpenseInfo?

    @Upsert
    suspend fun upsert(task: ExpenseInfo)

    @Delete
    suspend fun deleteById(expenseInfo: ExpenseInfo)
}
