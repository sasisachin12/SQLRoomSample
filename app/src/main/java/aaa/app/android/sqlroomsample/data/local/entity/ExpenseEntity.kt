package aaa.app.android.sqlroomsample.data.local.entity

import aaa.app.android.sqlroomsample.domain.model.Expense
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val date: Long,
    val expense: String,
    val amount: String
)

fun ExpenseEntity.toExpense(): Expense {
    return Expense(
        id = id,
        date = date,
        expense = expense,
        amount = amount
    )
}

fun Expense.toExpenseEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        date = date,
        expense = expense,
        amount = amount
    )
}
