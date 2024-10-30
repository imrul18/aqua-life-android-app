package com.imrul.aqua_life.dataset

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.imrul.aqua_life.MainApplication
import com.imrul.aqua_life.R
import com.imrul.aqua_life.ui.theme.KaffeeDark
import com.imrul.aqua_life.ui.theme.LimonadeDark
import com.imrul.aqua_life.ui.theme.SaftDark
import com.imrul.aqua_life.ui.theme.TeeDark
import com.imrul.aqua_life.ui.theme.WasserDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.UUID

class DrinkDataModel : ViewModel() {
    val drinkDao = MainApplication.database.drinkDataDao()

    var drinkListForPie : LiveData<List<DrinkDataPie>> = drinkDao.getDrinkListForPie()
    var drinkListForList : LiveData<List<DrinkData>> = drinkDao.getDrinkData()

    fun drinkListForLineChart(): LiveData<List<Float>> {
        var list = MediatorLiveData<List<Float>>()
        val profileLiveData = drinkDao.getProfileInfo()
        val drinkLiveData = drinkDao.getLiveDrinkDataList()

        list.addSource(profileLiveData) { profileData ->
            val drinkDataList = drinkLiveData.value
            if (drinkDataList != null) {
                val calculatedList = mutableListOf<Float>()

                var totalAmount = 0f
                var totalBurden = 0f

                for (drink in drinkDataList) {
                    val details = getBeverageTypeDetails(drink.type.toString())
                    totalAmount += drink.amount * details.hydration
                    totalBurden += drink.amount * details.burden
                    if(totalAmount < 0) {
                        totalAmount = 0f
                    }
                    val quantity = totalAmount / ((profileData.drinkGoalBurdenInLiters * 1000 ) + totalBurden) * 100
                    calculatedList.add(quantity)
                }
                list.value = calculatedList
            }
        }

        list.addSource(drinkLiveData) { drinkDataList ->
            val profileData = profileLiveData.value
            if (profileData != null) {
                val calculatedList = mutableListOf<Float>()

                var totalAmount = 0f
                var totalBurden = 0f

                for (drink in drinkDataList) {
                    val details = getBeverageTypeDetails(drink.type.toString())
                    totalAmount += drink.amount * details.hydration
                    totalBurden += drink.amount * details.burden
                    if(totalAmount < 0) {
                        totalAmount = 0f
                    }
                    val quantity = totalAmount / ((profileData.drinkGoalBurdenInLiters * 1000 ) + totalBurden) * 100
                    calculatedList.add(quantity)
                }
                list.value = calculatedList
            }
        }
        return list
    }

    fun drinkListTotal(): Flow<DrinkDataTotal> {
        return combine(
            drinkDao.getTotalDrinkFlow(),
            drinkDao.getProfileData()
        ) { drinkList, profileData ->
            var totalAmount = 0f
            var totalBurden = 0f
            drinkList.forEach {
                val details = getBeverageTypeDetails(it.type.toString())
                totalAmount += it.amount * details.hydration
                totalBurden += it.amount * details.burden
                if(totalAmount < 0) {
                    totalAmount = 0f
                }
            }
            val quantity = totalAmount / ((profileData.drinkGoalBurdenInLiters * 1000 ) + totalBurden) * 100
            val points = profileData.rewardPointsEarned
            DrinkDataTotal(quantity, points)
        }
    }

    fun addDrinkData(drinkData: DrinkData, onResult: (UUID) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                drinkDao.addDrinkData(drinkData)
                onResult(drinkData.id)
            } catch (e: Exception) {
                Log.e("DrinkDataModel", "Error adding data: ${e.localizedMessage}")
            }
        }
    }

    fun updateDrinkDataTypeById(id: UUID, type: beverageType) {
        viewModelScope.launch (Dispatchers.IO) {
            drinkDao.updateDrinkData(id, type)
        }
    }

    fun updateDrinkDataAmountById(id: UUID, amount: Float) {
        viewModelScope.launch (Dispatchers.IO) {
            drinkDao.updateDrinkDataAmount(id, amount)
        }
    }

    fun deleteDrinkDataById(id: UUID) {
        viewModelScope.launch (Dispatchers.IO) {
            drinkDao.deleteDrinkDataById(id)
        }
    }

    fun deleteAllDrinkData() {
        viewModelScope.launch (Dispatchers.IO) {
            drinkDao.deleteAllDrinkData()
        }
    }

    fun getBeverageTypeDetails(type: String) : beverageTypeDetails {
        return when(type.let { beverageType.valueOf(it) }) {
            beverageType.WASSER -> beverageTypeDetails(WasserDark, "Wasser", R.drawable.wasser_glas, listOf(
                Color(0xFFB4D4E8), Color(0xFF77B5DB), Color(0xFFB4D4E8), Color(0xFF77B5DB)), 1f, 0f)
            beverageType.KAFFEE -> beverageTypeDetails(KaffeeDark, "Kaffee", R.drawable.kaffee_glas, listOf(KaffeeDark, KaffeeDark, KaffeeDark, KaffeeDark), 0f, 1f)
            beverageType.TEE -> beverageTypeDetails(TeeDark, "Tee", R.drawable.tee_glas, listOf(TeeDark, TeeDark, TeeDark, TeeDark), 0.5f, 0f)
            beverageType.SAFT -> beverageTypeDetails(SaftDark, "Saft", R.drawable.saft_glas, listOf(SaftDark, SaftDark, SaftDark, SaftDark), 0f, 1f)
            beverageType.LIMONADE -> beverageTypeDetails(LimonadeDark, "Limonade", R.drawable.limonade_glas, listOf(LimonadeDark, LimonadeDark, LimonadeDark, LimonadeDark), 0f, 2f)
        }
    }
}