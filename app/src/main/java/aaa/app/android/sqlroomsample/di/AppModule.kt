package aaa.app.android.sqlroomsample.di

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.data.DefaultExpenseRepository
import aaa.app.android.sqlroomsample.data.ExpenseRepository
import aaa.app.android.sqlroomsample.db.ToDoDatabase
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): ToDoDatabase {
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            "todo_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(taskDao: TaskDao): ExpenseRepository {
        return DefaultExpenseRepository(taskDao)
    }

    @Singleton
    @Provides
    fun provideExpenseDao(database: ToDoDatabase): TaskDao = database.taskDao()
}
