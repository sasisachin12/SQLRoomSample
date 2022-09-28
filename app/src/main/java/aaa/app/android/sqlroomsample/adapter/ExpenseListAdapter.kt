package aaa.app.android.sqlroomsample.adapter

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseListAdapter internal constructor(
    context: Context, val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<ExpenseInfo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vdate: TextView = itemView.findViewById(R.id.date)
        val expense: TextView = itemView.findViewById(R.id.expense)
        val expense_amount: TextView = itemView.findViewById(R.id.et_expense_amount)
        val delete: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_expense_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = expenseList[position]
        holder.vdate.text = current.date
        holder.expense.text = current.expense
        holder.expense_amount.text = current.amount
        holder.delete.setOnClickListener {

            itemClickListener.onClick(current)

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<ExpenseInfo>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = expenseList.size


}