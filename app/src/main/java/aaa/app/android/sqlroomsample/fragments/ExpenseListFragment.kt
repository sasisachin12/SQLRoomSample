package aaa.app.android.sqlroomsample.fragments


import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.adapter.ExpenseListAdapter
import aaa.app.android.sqlroomsample.adapter.ItemClickListener
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class ExpenseListFragment : Fragment(), ItemClickListener {

    private lateinit var expenseViewModel: ExpenseViewModel
    private var dateFilter = false
    private var expenseFilter = false
    private var amountFilter = false
    private var orderedList = emptyList<ExpenseInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        val adapter = ExpenseListAdapter(requireActivity(), this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())


        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer<List<ExpenseInfo>>
        { expenseList ->

            val totalAmount: Int = expenseList.sumOf { it.amount.toInt() }
            display_amount.text = totalAmount.toString()
            expenseList?.let { adapter.setWords(it) }

            date.setOnClickListener {
                dateFilter = !dateFilter
                setDateDrawable(dateFilter)
                orderedList = if (dateFilter) {
                    expenseList.sortedBy {
                        it.date
                    }
                } else {
                    expenseList.sortedByDescending {
                        it.date
                    }
                }
                adapter.setWords(orderedList)
            }

            expense.setOnClickListener {
                expenseFilter = !expenseFilter
                setExpenseDrawable(expenseFilter)
                orderedList = if (expenseFilter) {
                    expenseList.sortedBy {
                        it.expense
                    }
                } else {
                    expenseList.sortedByDescending {
                        it.expense
                    }
                }
                adapter.setWords(orderedList)
            }

            expense_amount.setOnClickListener {
                amountFilter = !amountFilter
                setAmountDrawable(amountFilter)
                orderedList = if (amountFilter) {
                    expenseList.sortedBy {
                        it.amount
                    }
                } else {
                    expenseList.sortedByDescending {
                        it.amount
                    }
                }
                adapter.setWords(orderedList)
            }

        })


    }

    override fun onClick(item: Any) {
        if (item is ExpenseInfo) {
            lifecycleScope.launchWhenResumed {
                item.id?.let { expenseViewModel.deleteByID(it) }
            }
        }
    }

    private fun setAmountDrawable(state: Boolean) {
        if (state) {
            expense_amount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )

        } else {
            expense_amount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_down,
                0
            )
        }
        date.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        expense.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

    }

    private fun setExpenseDrawable(state: Boolean) {
        if (state) {
            expense.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )

        } else {
            expense.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_down,
                0
            )
        }
        date.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        expense_amount.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

    }

    private fun setDateDrawable(state: Boolean) {
        if (state) {
            date.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )

        } else {
            date.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_down,
                0
            )
        }
        expense_amount.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        expense.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
    }
}