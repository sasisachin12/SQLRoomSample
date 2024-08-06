package aaa.app.android.sqlroomsample.db

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseInfo::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
