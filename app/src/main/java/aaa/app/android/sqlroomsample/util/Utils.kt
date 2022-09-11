package aaa.app.android.sqlroomsample.util

import aaa.app.android.sqlroomsample.util.APPConstant.dateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calender.time)
    }
}