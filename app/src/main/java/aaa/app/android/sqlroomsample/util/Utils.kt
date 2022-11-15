package aaa.app.android.sqlroomsample.util

import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return sdf.format(calender.time)
    }

    fun getCurrentTime(): String {
        val calender = Calendar.getInstance()
        val sdf = SimpleDateFormat(TIME_FORMAT_ONE, Locale.getDefault())
        return sdf.format(calender.time)
    }


    fun TextInputEditText.transformIntoDatePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false
        isCursorVisible = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }


    fun TextInputEditText.transformIntoTimePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false
        isCursorVisible = false

        val myCalendar = Calendar.getInstance()

        val timePickerOnDataSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)

            setText(SimpleDateFormat(format, Locale.getDefault()).format(myCalendar.time))
        }


        setOnClickListener {
            TimePickerDialog(
                context,
                timePickerOnDataSetListener,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    fun stringtoDate(date: String): Date? {
        val formatter = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return formatter.parse(date)
    }

    fun get24Hours(time: String):String {
        val displayFormat = SimpleDateFormat("YYYY-MM-DD HH:MM:SS", Locale.getDefault())
        val parseFormat = SimpleDateFormat("dd-mm-yyyy hh:mm a",Locale.getDefault())
        val date=parseFormat.parse(time)
        return displayFormat.format(date)
    }


}