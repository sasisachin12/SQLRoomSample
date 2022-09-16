package aaa.app.android.sqlroomsample.util

import aaa.app.android.sqlroomsample.util.APPConstant.DATE_FORMAT_ONE
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return sdf.format(calender.time)
    }
}