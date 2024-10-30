package com.imrul.aqua_life.dataset

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PointDataDao {
    @Query("SELECT * FROM PointData")
    fun getPointData() : LiveData<List<PointData>>

    @Insert
    fun addPointData(pointData: PointData)
}