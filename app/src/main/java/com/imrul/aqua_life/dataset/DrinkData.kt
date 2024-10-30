package com.imrul.aqua_life.dataset

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.UUID

@Entity(tableName = "DrinkData")
data class DrinkData(
    @PrimaryKey val id : UUID = UUID.randomUUID(),
    val amount : Float,
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    val type : beverageType,
    val isSynced : Boolean = false,
)

data class DrinkDataPie(
    val value : Float,
    val label : String,
)

data class DrinkDataTotal(
    val quantity : Float = 0f,
    val points : Int = 0,
)

enum class beverageType {
    WASSER, KAFFEE, TEE, SAFT, LIMONADE
}

data class beverageTypeDetails(
    val color : Color,
    val label : String,
    val icon : Int,
    val gradient : List<Color> = listOf(Color.White, Color.White, Color.White, Color.White),
    val hydration : Float = 1f,
    val burden : Float = 1f,
)

