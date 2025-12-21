package aaa.app.android.sqlroomsample.db

import aaa.app.android.sqlroomsample.dao.TaskDao
import aaa.app.android.sqlroomsample.data.local.entity.ExpenseEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseEntity::class], version = 2, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
