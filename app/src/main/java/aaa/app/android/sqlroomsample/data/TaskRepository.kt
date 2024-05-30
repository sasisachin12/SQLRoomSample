package aaa.app.android.sqlroomsample.data

import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasksStream(): Flow<List<ExpenseInfo>>

    suspend fun getTasks(forceUpdate: Boolean = false): List<ExpenseInfo>

    suspend fun refresh()

    fun getTaskStream(taskId: String): Flow<ExpenseInfo?>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): ExpenseInfo?

    suspend fun refreshTask(taskId: String)

    suspend fun createTask(title: String, description: String): String

    suspend fun updateTask(taskId: String, title: String, description: String)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}
