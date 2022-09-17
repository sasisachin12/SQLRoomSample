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
    private var allExpenseList = mutableListOf<ExpenseInfo>()
    private var dateState = false
    private var expenseState = false
    private var amountState = false
    private var orderedList:List<ExpenseInfo> = emptyList()

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
            allExpenseList.clear()
            val totalAmount: Int = expenseList.sumOf { it.amount.toInt() }
            display_amount.text = totalAmount.toString()
            allExpenseList.addAll(expenseList)
            expenseList?.let { adapter.setWords(it) }
        })

        date.setOnClickListener {
            dateState = !dateState
            orderedList = if (dateState) {
                date.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24,0)
                allExpenseList.sortedBy {
                    it.date
                }
            } else {
                date.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0)
                allExpenseList.sortedByDescending {
                    it.date
                }
            }

            adapter.setWords(orderedList)

        }
        expense.setOnClickListener {
            expenseState = !expenseState
            orderedList = if (expenseState) {
                expense.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24,0)
                allExpenseList.sortedBy {
                    it.expense
                }
            } else {
                expense.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0)
                allExpenseList.sortedByDescending {
                    it.expense
                }
            }
            adapter.setWords(orderedList)
        }
        expense_amount.setOnClickListener {
            amountState = !amountState
            orderedList = if (amountState) {
                expense_amount.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24,0)
                allExpenseList.sortedBy {
                    it.amount
                }
            } else {
                expense_amount.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0)
                allExpenseList.sortedByDescending {
                    it.amount
                }
            }
            adapter.setWords(orderedList)
        }

    }

    override fun onClick(item: Any) {
        if (item is ExpenseInfo) {
            lifecycleScope.launchWhenResumed {
                item.id?.let { expenseViewModel.deleteByID(it) }
            }
        }
    }
}