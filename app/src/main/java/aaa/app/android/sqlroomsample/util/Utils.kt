package aaa.app.android.sqlroomsample.util

import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import aaa.app.android.sqlroomsample.util.APPConstant.TIME_FORMAT_ONE
import android.text.TextUtils
import androidx.core.util.PatternsCompat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

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


    fun convertDateToLong(
        date: String,
        format: String
    ): Long {
        val df = SimpleDateFormat(format, Locale.getDefault())
        return df.parse(date)?.time ?: 0
    }


    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("$DATE_FORMAT_ONE $TIME_FORMAT_ONE", Locale.getDefault())
        return format.format(date)
    }

    fun getCurrentMonthStart(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DATE] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val monthFirstDay = calendar.time
        val df = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return df.format(monthFirstDay)

    }

    fun getCurrentMonthEnd(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthLastDay = calendar.time
        val df = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return df.format(monthLastDay)
    }

    fun getCurrentMonthName(): String {
        val calendar = Calendar.getInstance()
        val monthDate = SimpleDateFormat("MMMM", Locale.getDefault())
        return monthDate.format(calendar.time)
    }


    fun validateEmail(email: String?): Boolean {
        return !TextUtils.isEmpty(email) && email?.let {
            PatternsCompat.EMAIL_ADDRESS.matcher(it).matches()
        } == true
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return formatter.format(Date(millis))

    }

    fun formattedTime(hour: Int, minute: Int): String {
        val formatter = DateTimeFormatter.ofPattern(TIME_FORMAT_ONE)
        val time = LocalTime.of(hour, minute).format(formatter)
        return time
    }

    fun numberToRupees(numb: Int): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        return formatter.format(numb)
    }

}