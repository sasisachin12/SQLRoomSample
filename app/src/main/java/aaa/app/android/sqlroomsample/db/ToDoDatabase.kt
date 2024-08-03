package aaa.app.android.sqlroomsample.db

import aaa.app.android.sqlroomsample.dao.ExpenseDao
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseInfo::class], version = 2, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): ExpenseDao
}
