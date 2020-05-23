package aaa.app.android.sqlroomsample.ui.dashboard

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.viewmodel.ExpenseViewModel
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : Fragment() {
    private var dashboardViewModel: DashboardViewModel? = null
    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long>? = MutableLiveData()


    var picker: DatePickerDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        expenseViewModel = ViewModelProvider(requireActivity()).get(ExpenseViewModel::class.java)
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root =
            inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clender = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formatedDate = sdf.format(clender.getTime())
        expense_date.setText( formatedDate)



        insertResponse?.observe(viewLifecycleOwner,
            Observer<Long?> {
                Toast.makeText(
                    requireActivity(),
                    R.string.added_success_msg,
                    Toast.LENGTH_LONG
                ).show()
                expense_for.text.clear()
                expense_amount.text.clear()
            })






        expense_date.setOnClickListener {

            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            // date picker dialog
            picker = DatePickerDialog(
                requireActivity(),
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    val formatedDate = sdf.format(cldr.getTime())
                    //  val date =

                    expense_date.setText(formatedDate)
                },
                year,
                month,
                day
            )
            picker!!.show()


        }








        button_save.setOnClickListener {

            val date = expense_date.text.toString()
            val expense = expense_for.text.toString()
            val expense_amount = expense_amount.text.toString()
            if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(expense) && !TextUtils.isEmpty(
                    expense_amount
                )
            ) {
                val word = ExpenseInfo(
                    null,
                    date = date.toString(),
                    expense = expense,
                    amount = expense_amount
                )

                lifecycleScope.launchWhenResumed {

                    insertResponse?.postValue(expenseViewModel.insert(word))
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }
            //  }

        }
    }
}