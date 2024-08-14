package aaa.app.android.sqlroomsample.data

import aaa.app.android.sqlroomsample.entity.ExpenseInfo

interface TaskRepository {

    // fun getTasksStream(): Flow<List<ExpenseInfo>>

    suspend fun getTasks(): List<ExpenseInfo>

    suspend fun refresh()

    //fun getTaskStream(taskId: String): Flow<ExpenseInfo?>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): ExpenseInfo?

    suspend fun refreshTask(taskId: String)

    suspend fun createTask(
        date: Long,
        expense: String,
        amount: String,
        isCompleted: Boolean
    ): String

    suspend fun updateTask(taskId: String, title: String, description: String)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}
