package com.imrul.aqua_life.dataset

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ProfileDataDao {
    @Query("SELECT * FROM ProfileData WHERE status = 2")
    fun getProfileData(): Flow<ProfileData>

    @Query("SELECT * FROM ProfileData WHERE id = :id")
    fun getProfileDataByID(id: UUID): LiveData<ProfileData>

    @Query("SELECT * FROM ProfileData ORDER BY id DESC LIMIT 1")
    fun getLastProfileData(): LiveData<ProfileData>

    @Insert
    suspend fun addProfileData(profileData: ProfileData)

    @Update
    fun updateProfileData(profileData: ProfileData)
}