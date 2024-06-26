package aaa.app.android.sqlroomsample.dao

import androidx.room.TypeConverter
import java.util.*

class DateConverters {

        @TypeConverter
        fun toDate(dateLong: Long?): Date? {
            return dateLong?.let { Date(it) }
        }

        @TypeConverter
        fun fromDate(date: Date?): Long? {
            return date?.time
        }

}