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
            val totalAmount: Long = expenseList.sumOf { it.amount.toLong() }
            display_amount.text = totalAmount.toString()
            expenseList?.let { adapter.setWords(it) }
        })


    }

    override fun onClick(item: Any) {
        if (item is ExpenseInfo) {
            lifecycleScope.launchWhenResumed {
                item.id?.let { expenseViewModel.deleteByID(it) }
            }
        }
    }
}