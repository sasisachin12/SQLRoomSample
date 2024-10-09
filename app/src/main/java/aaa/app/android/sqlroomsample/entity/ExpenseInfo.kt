package aaa.app.android.sqlroomsample.entity

import aaa.app.android.sqlroomsample.dao.DateConverters
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "expense_table")
class ExpenseInfo(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @field:TypeConverters(DateConverters::class)
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "expense") val expense: String,
    @ColumnInfo(name = "amount") val amount: String,

    )