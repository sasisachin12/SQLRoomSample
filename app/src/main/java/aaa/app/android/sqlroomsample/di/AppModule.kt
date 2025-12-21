package aaa.app.android.sqlroomsample.di

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.data.repository.ExpenseRepositoryImpl
import aaa.app.android.sqlroomsample.db.ToDoDatabase
import aaa.app.android.sqlroomsample.domain.repository.ExpenseRepository
import aaa.app.android.sqlroomsample.domain.use_case.AddExpenseUseCase
import aaa.app.android.sqlroomsample.domain.use_case.DeleteExpenseUseCase
import aaa.app.android.sqlroomsample.domain.use_case.GetExpensesUseCase
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
    fun provideExpenseRepository(db: ToDoDatabase): ExpenseRepository {
        return ExpenseRepositoryImpl(db.taskDao())
    }

    @Singleton
    @Provides
    fun provideExpenseDao(database: ToDoDatabase): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideGetExpensesUseCase(repository: ExpenseRepository): GetExpensesUseCase {
        return GetExpensesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteExpenseUseCase(repository: ExpenseRepository): DeleteExpenseUseCase {
        return DeleteExpenseUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddExpenseUseCase(repository: ExpenseRepository): AddExpenseUseCase {
        return AddExpenseUseCase(repository)
    }
}
