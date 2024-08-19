package aaa.app.android.sqlroomsample.di

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.data.DefaultExpenseRepository
import aaa.app.android.sqlroomsample.data.ExpenseRepository
import aaa.app.android.sqlroomsample.db.ToDoDatabase
import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "newexpensive"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(database: ToDoDatabase): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideDefaultTaskRepository(
        taskDao: TaskDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope
    ): DefaultExpenseRepository =
        DefaultExpenseRepository(localDataSource = taskDao, dispatcher, scope)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultExpenseRepository): ExpenseRepository


}

