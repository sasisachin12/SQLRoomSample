package aaa.app.android.sqlroomsample.fragments

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.databinding.FragmentExpenseBinding
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.util.transformIntoDatePicker
import aaa.app.android.sqlroomsample.util.transformIntoTimePicker
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_expense.*


class ExpenseFragment : Fragment(R.layout.fragment_expense) {

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExpenseBinding.bind(view)

        binding.etExpenseDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
        binding.etExpenseTime.transformIntoTimePicker(requireContext(), "hh.mm a")


//        et_expense_date.text = getCurrentDate()
//        insertResponse.observe(
//            viewLifecycleOwner
//        ) {
//            Toast.makeText(
//                requireActivity(),
//                R.string.added_success_msg,
//                Toast.LENGTH_LONG
//            ).show()
//            et_expense_for.text?.clear()
//            et_expense_amount.text?.clear()
//        }



        button_save.setOnClickListener {

            val date = et_expense_date.text.toString()
            val expense = et_expense_for.text.toString()
            val expenseAmount = et_expense_amount.text.toString()
            if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(expense) && !TextUtils.isEmpty(
                    expenseAmount
                )
            ) {
                val word = ExpenseInfo(
                    null,
                    date = date,
                    expense = expense,
                    amount = expenseAmount
                )

                lifecycleScope.launchWhenResumed {

                    insertResponse.postValue(expenseViewModel.insert(word))
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}