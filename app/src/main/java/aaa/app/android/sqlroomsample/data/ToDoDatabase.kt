package aaa.app.android.sqlroomsample.data

import aaa.app.android.sqlroomsample.dao.ExpenseDao
import aaa.app.android.sqlroomsample.db.ExpenseRoomDatabase
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseInfo::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): ExpenseDao
}
