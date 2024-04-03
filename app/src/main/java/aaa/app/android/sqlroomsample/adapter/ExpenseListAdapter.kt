package aaa.app.android.sqlroomsample.adapter

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.util.Utils.convertLongToTime
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseListAdapter internal constructor(
    context: Context, private val listItemClickListener: ListItemClickListener
) : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<ExpenseInfo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayDate: TextView = itemView.findViewById(R.id.date)
        val expense: TextView = itemView.findViewById(R.id.expense)
        val expenseAmount: TextView = itemView.findViewById(R.id.et_expense_amount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_expense_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]
        holder.displayDate.text = convertLongToTime(item.date)
        holder.expense.text = item.expense
        holder.expenseAmount.text = item.amount
        holder.displayDate.setOnClickListener {
            listItemClickListener.onDeleteItemClick(item)

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<ExpenseInfo>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = expenseList.size


}

interface ListItemClickListener {

    fun onDeleteItemClick(item: Any)
}