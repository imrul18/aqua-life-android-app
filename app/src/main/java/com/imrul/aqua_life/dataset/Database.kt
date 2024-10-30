package com.imrul.aqua_life.dataset

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imrul.aqua_life.assets.Converters

@Database(entities = [PointData::class, DrinkData::class, ProfileData::class], version = 3)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    companion object {
        const val NAME = "aqua_db"
    }

    abstract fun pointDataDao() : PointDataDao

    abstract fun drinkDataDao() : DrinkDataDao

    abstract fun profileDataDao() : ProfileDataDao

}