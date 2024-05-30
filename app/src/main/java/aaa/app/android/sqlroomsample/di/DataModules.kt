package aaa.app.android.sqlroomsample.di

import aaa.app.android.sqlroomsample.dao.ExpenseDao
import aaa.app.android.sqlroomsample.data.DefaultTaskRepository
import aaa.app.android.sqlroomsample.data.TaskRepository
import aaa.app.android.sqlroomsample.data.ToDoDatabase
import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultTaskRepository): TaskRepository
}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "newexpensive.db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: ToDoDatabase): ExpenseDao = database.taskDao()
}
