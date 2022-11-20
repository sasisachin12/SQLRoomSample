package aaa.app.android.sqlroomsample.fragments

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.databinding.FragmentExpenseBinding
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.convertDateToLong
import aaa.app.android.sqlroomsample.util.Utils.getCurrentDate
import aaa.app.android.sqlroomsample.util.Utils.getCurrentTime
import aaa.app.android.sqlroomsample.util.Utils.transformIntoDatePicker
import aaa.app.android.sqlroomsample.util.Utils.transformIntoTimePicker
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope


class ExpenseFragment : Fragment(R.layout.fragment_expense) {

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExpenseBinding.bind(view)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        binding.etExpenseDate.transformIntoDatePicker(requireContext(), DATE_FORMAT_ONE)
        binding.etExpenseTime.transformIntoTimePicker(requireContext(), TIME_FORMAT_ONE)
        binding.etExpenseDate.setText(getCurrentDate())
        binding.etExpenseTime.setText(getCurrentTime())


        insertResponse.observe(
            viewLifecycleOwner
        ) {
            Toast.makeText(
                requireActivity(),
                R.string.added_success_msg,
                Toast.LENGTH_LONG
            ).show()
            binding.etExpenseFor.text?.clear()
            binding.etExpenseAmount.text?.clear()
        }


        binding.buttonSave.setOnClickListener {

            val dateandTime = binding.etExpenseDate.text.toString() +" "+ binding.etExpenseTime.text.toString()
            val expense = binding.etExpenseFor.text.toString()
            val expenseAmount = binding.etExpenseAmount.text.toString()
            if (!TextUtils.isEmpty(dateandTime) && !TextUtils.isEmpty(expense) && !TextUtils.isEmpty(
                    expenseAmount
                )
            ) {
                val word = ExpenseInfo(
                    null,
                    date = convertDateToLong(dateandTime,"$DATE_FORMAT_ONE $TIME_FORMAT_ONE"),
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