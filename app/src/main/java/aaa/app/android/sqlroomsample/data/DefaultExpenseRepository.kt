package aaa.app.android.sqlroomsample.data

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.di.ApplicationScope
import aaa.app.android.sqlroomsample.di.DefaultDispatcher
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultExpenseRepository @Inject constructor(

    private val localDataSource: TaskDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ExpenseRepository {

    override suspend fun addExpense(
        expenseInfo: ExpenseInfo
    ): String {

        val taskId = withContext(dispatcher) {
            UUID.randomUUID().toString()
        }
        localDataSource.upsert(expenseInfo)
        return taskId
    }


    override val myModels: Flow<List<ExpenseInfo>> = localDataSource.getAll()

    override suspend fun deleteTask(taskId: ExpenseInfo) {
        localDataSource.deleteById(taskId)

    }

}
