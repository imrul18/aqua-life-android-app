package com.imrul.aqua_life.assets

import androidx.room.TypeConverter
import com.imrul.aqua_life.dataset.beverageType
import java.sql.Timestamp

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it) }
    }

    @TypeConverter
    fun dateToTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time
    }

    @TypeConverter
    fun fromBeverageType(value: beverageType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toBeverageType(value: String?): beverageType? {
        return value?.let { beverageType.valueOf(it) }
    }
}