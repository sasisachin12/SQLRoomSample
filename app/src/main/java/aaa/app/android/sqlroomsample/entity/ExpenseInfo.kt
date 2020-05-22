package aaa.app.android.sqlroomsample.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
class ExpenseInfo(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "expense") val expense: String,
    @ColumnInfo(name = "amount") val amount: String
)