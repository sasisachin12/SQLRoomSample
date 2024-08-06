package aaa.app.android.sqlroomsample.dao

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM expense_table WHERE id = :taskId")
    fun observeById(taskId: String): Flow<ExpenseInfo>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM expense_table")
    suspend fun getAll(): List<ExpenseInfo>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM expense_table WHERE id = :taskId")
    suspend fun getById(taskId: String): ExpenseInfo?

    /**
     * Insert or update a task in the database. If a task already exists, replace it.
     *
     * @param task the task to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(task: ExpenseInfo)

    /**
     * Insert or update tasks in the database. If a task already exists, replace it.
     *
     * @param tasks the tasks to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(tasks: List<ExpenseInfo>)

    /**
     * Update the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE expense_table SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM expense_table WHERE id = :taskId")
    suspend fun deleteById(taskId: String): Int

    /* */
    /**
     * Delete all tasks.
     *//*
    @Query("DELETE FROM task")
    suspend fun deleteAll()*/

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM expense_table WHERE isCompleted = 1")
    suspend fun deleteCompleted(): Int

}
