package aaa.app.android.sqlroomsample.ui.home


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
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), ItemClickListener {
    private var homeViewModel: HomeViewModel? = null
    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity()).get(ExpenseViewModel::class.java)
        val adapter = ExpenseListAdapter(requireActivity(), this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())


        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer<List<ExpenseInfo>>
        { expenseList ->
            val totalAmount: Int = expenseList.map { it.amount.toInt() }.sum()
            display_amount.setText(totalAmount.toString())
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