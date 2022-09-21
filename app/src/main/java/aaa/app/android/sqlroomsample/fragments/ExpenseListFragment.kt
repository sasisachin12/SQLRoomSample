package aaa.app.android.sqlroomsample.fragments


import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.adapter.ExpenseListAdapter
import aaa.app.android.sqlroomsample.adapter.ItemClickListener
import aaa.app.android.sqlroomsample.databinding.FragmentExpenseListingBinding
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager


class ExpenseListFragment : Fragment((R.layout.fragment_expense_listing)), ItemClickListener {

    private lateinit var expenseViewModel: ExpenseViewModel
    private var dateFilter = false
    private var expenseFilter = false
    private var amountFilter = false
    private var orderedList = emptyList<ExpenseInfo>()
    private val originalList = mutableListOf<ExpenseInfo>()

    var _binding: FragmentExpenseListingBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExpenseListingBinding.bind(view)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        val adapter = ExpenseListAdapter(requireActivity(), this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())


        expenseViewModel.allExpenses.observe(viewLifecycleOwner) { expenseList ->
            val totalAmount: Int = expenseList.sumOf { it.amount.toInt() }
            binding.displayAmount.text = totalAmount.toString()
            expenseList?.let { adapter.setAdapter(it) }
            originalList.clear()
            originalList.addAll(expenseList)
        }
        binding.date.setOnClickListener {
            dateFilter = !dateFilter
            setDateDrawable(dateFilter)
            orderedList = if (dateFilter) {
                originalList.sortedBy {
                    it.date
                }
            } else {
                originalList.sortedByDescending {
                    it.date
                }
            }
            adapter.setAdapter(orderedList)
        }

        binding.expense.setOnClickListener {
            expenseFilter = !expenseFilter
            setExpenseDrawable(expenseFilter)
            orderedList = if (expenseFilter) {
                originalList.sortedBy {
                    it.expense
                }.toMutableList()
            } else {
                originalList.sortedByDescending {
                    it.expense
                }.toMutableList()
            }
            adapter.setAdapter(orderedList)
        }

        binding.etExpenseAmount.setOnClickListener {
            amountFilter = !amountFilter
            setAmountDrawable(amountFilter)
            orderedList = if (amountFilter) {
                originalList.sortedBy {
                    it.amount.toInt()
                }

            } else {
                originalList.sortedByDescending {
                    it.amount.toInt()
                }

            }
            adapter.setAdapter(orderedList)

        }


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
            binding.etExpenseAmount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )

        } else {
            binding.etExpenseAmount.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_down,
                0
            )
        }
        binding.date.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        binding.expense.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

    }

    private fun setExpenseDrawable(state: Boolean) {
        if (state) {
            binding.expense.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )

        } else {
            binding.expense.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_down,
                0
            )
        }
        binding.date.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        binding.etExpenseAmount.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)

    }

    private fun setDateDrawable(state: Boolean) {
        if (state) {
            binding.date.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )

        } else {
            binding.date.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_down,
                0
            )
        }
        binding.etExpenseAmount.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        binding.expense.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}