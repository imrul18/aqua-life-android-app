package com.imrul.aqua_life.dataset

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.aqua_life.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.launch
import java.util.UUID

class ProfileDataModel : ViewModel() {
    val profileDao = MainApplication.database.profileDataDao()

    fun getSexList(): List<String> {
        return listOf("male", "female")
    }

    fun getGermanSex(sex: String): String {
        return when (sex) {
            "male" -> "Mann"
            "female" -> "Frau"
            else -> "Mann"
        }
    }

    fun getJobActivityList(): List<String> {
        return listOf("mostlySeated", "mostlyStanding", "heavyWorking", "extreme")
    }
    fun getGermanJobActivity(jobActivity: String): String {
        return when (jobActivity) {
            "mostlySeated" -> "sitzende Tätigkeit"
            "mostlyStanding" -> "stehende Tätigkeit"
            "heavyWorking" -> "Anstrengende Tätigkeit"
            "extreme" -> "Extreme Tätigkeit"
            else -> "sitzende Tätigkeit"
        }
    }

    fun getSportsActivityList(): List<String> {
        return listOf("none", "light", "moderate", "high")
    }

    fun getGermanSportsActivity(sportsActivity: String): String {
        return when (sportsActivity) {
            "none" -> "Kein Sport"
            "light" -> "2-4h / Woche"
            "moderate" -> "4-6h / Woche"
            "high" -> ">8h / Woche"
            else -> "Kein Sport"
        }
    }

    fun getBottleWaterTypeList(): List<String> {
        return listOf("none", "reverseOsmosis", "charcoalFilter", "distilledWater")
    }

    fun getGermanBottleWaterType(bottleWaterType: String): String {
        return when (bottleWaterType) {
            "none" -> "Ungefiltert"
            "reverseOsmosis" -> "Umkehrosmose"
            "charcoalFilter" -> "Kannenfilter"
            "distilledWater" -> "Destill. Wasser"
            else -> "Ungefiltert"
        }
    }


    fun profileData(): Flow<ProfileData> {
        return profileDao.getProfileData()
    }

    fun getProfileData(id: UUID): LiveData<ProfileData>{
        return profileDao.getProfileDataByID(id)
    }

    fun getLastProfileData(): LiveData<ProfileData>{
        return profileDao.getLastProfileData()
    }

    fun addProfileData(profileData : ProfileData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileDao.addProfileData(profileData)
            } catch (e: Exception) {
                Log.e("DrinkDataModel", "Error adding data: ${e.localizedMessage}")
            }
        }
    }

    fun updateProfileData(profileData : ProfileData) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                profileDao.updateProfileData(profileData)
            } catch (e: Exception) {
                Log.e("DrinkDataModel", "Error updating data: ${e.localizedMessage}")
            }
        }
    }

    fun getDrinkGoalBurdenInLiters(data : ProfileData) : Float {
        val weight = if (data.weight.isEmpty()) 0.0f else data.weight.toFloat()
        val weightBasedHydration = weight * 0.03f
        var sexAdjustment = if(data.sex == "male") 0.0f else -0.1f
        println("jobActivity: ${data.jobActivity}")
        val jobActivityPercentage =  when (data.jobActivity) {
            "mostlySeated" -> 0.0f
            "mostlyStanding" -> 0.1f
            "heavyWorking" -> 0.2f
            "extreme" -> 0.3f
            else -> 0.0f
        }
        val sportsActivityPercentage = when (data.sportsActivity) {
            "none" -> 0.0f
            "light" -> 0.05f
            "moderate" -> 0.15f
            "high" -> 0.3f
            else -> 0.0f
        }
        val bottleWaterTypePercentage = when (data.bottleWaterType) {
            "none" -> 0.0f
            "reverseOsmosis" -> -0.1f
            "charcoalFilter" -> -0.1f
            "distilledWater" -> 0.0f
            else -> 0.0f
        }

        val totalPercentage = jobActivityPercentage + sportsActivityPercentage + bottleWaterTypePercentage + sexAdjustment
        return weightBasedHydration * (1 + totalPercentage)
    }
}