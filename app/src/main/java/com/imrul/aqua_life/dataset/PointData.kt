package com.imrul.aqua_life.dataset

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PointData(
    @PrimaryKey(autoGenerate = true)
    val pointID: Int,
    val id : String,
    val thumbnail_url : String,
    val title : String,
    val subtitle : String,
    val costInPoints : Int
)
