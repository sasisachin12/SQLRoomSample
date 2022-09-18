package aaa.app.android.sqlroomsample.fragments

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.Utils.getCurrentDate
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_expense.*
import java.text.SimpleDateFormat
import java.util.*


class ExpenseFragment : Fragment() {

    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()


    private var picker: DatePickerDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        return inflater.inflate(R.layout.fragment_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        expense_date.text = getCurrentDate()
        insertResponse.observe(
            viewLifecycleOwner
        ) {
            if (expense_for.text!!.isNotEmpty() && expense_amount.text!!.isNotEmpty()){
                Toast.makeText(
                    requireActivity(),
                    "Expense Added Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            expense_for.text?.clear()
            expense_amount.text?.clear()
        }






        expense_date.setOnClickListener {

            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_MONTH]
            val month = calendar[Calendar.MONTH]
            val year = calendar[Calendar.YEAR]


            picker = DatePickerDialog(
                requireActivity(),
                { view, year, month, day ->
                    val dateFormatter = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
                    val d = Date(year, month, day)
                    val strDate: String = dateFormatter.format(d)
                    expense_date.text = strDate
                },
                year,
                month,
                day
            )
            picker?.show()


        }








        button_save.setOnClickListener {

            val date = expense_date.text.toString()
            val expense = expense_for.text.toString()
            val expenseAmount = expense_amount.text.toString()
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
}