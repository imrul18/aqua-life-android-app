package com.imrul.aqua_life.dataset

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

@Dao
interface DrinkDataDao {
    @Query("SELECT * FROM DrinkData WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay")
    fun getDrinkData(
        startOfDay: Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                     endOfDay: Long = LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    ): LiveData<List<DrinkData>>

    @Query("SELECT sum(amount) as value, type as label FROM DrinkData WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay GROUP BY type")
    fun getDrinkListForPie(
        startOfDay: Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        endOfDay: Long = LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    ): LiveData<List<DrinkDataPie>>


    @Query("SELECT * FROM DrinkData WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay")
    fun getTotalDrinkFlow(
        startOfDay: Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        endOfDay: Long = LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        ): Flow<List<DrinkData>>

    @Query("SELECT * FROM ProfileData LIMIT 1")
    fun getProfileData(): Flow<ProfileData>

    @Insert
    suspend fun addDrinkData(drinkData: DrinkData): Long

    // update drink data by ID
    @Query("UPDATE DrinkData SET type = :type WHERE id = :id")
    suspend fun updateDrinkData(id: UUID, type: beverageType)

    @Query("UPDATE DrinkData SET amount = :amount WHERE id = :id")
    suspend fun updateDrinkDataAmount(id: UUID, amount: Float)

    @Query("DELETE FROM DrinkData WHERE id = :id")
    suspend fun deleteDrinkDataById(id: UUID)

    @Query("DELETE FROM DrinkData")
    fun deleteAllDrinkData()


//    @Query("DELETE FROM DrinkData WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay")
//    fun deleteAllDrinkData(
//        startOfDay: Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
//        endOfDay: Long = LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
//    )

    @Query("SELECT * FROM DrinkData WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay AND timestamp <= :dateTime")
    fun getLiveDrinkDataList(
        startOfDay: Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        endOfDay: Long = LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        dateTime: Long = System.currentTimeMillis()
    ): LiveData<List<DrinkData>>

    @Query("SELECT * FROM ProfileData")
    fun getProfileInfo(): LiveData<ProfileData>

}