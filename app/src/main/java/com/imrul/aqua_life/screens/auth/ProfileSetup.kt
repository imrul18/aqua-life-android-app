package com.imrul.aqua_life.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.imrul.aqua_life.dataset.ProfileData
import com.imrul.aqua_life.dataset.ProfileDataModel
import com.imrul.aqua_life.navigation.Graph
import com.imrul.aqua_life.screens.auth.profileSetup.StepBody
import com.imrul.aqua_life.screens.auth.profileSetup.StepFooter
import com.imrul.aqua_life.screens.auth.profileSetup.StepHeader
import com.imrul.aqua_life.ui.theme.Background
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileSetup(
    navController: NavController,
) {
    val profileModel: ProfileDataModel = viewModel()
    val user by profileModel.profileData().asLiveData().observeAsState()

    var data by remember { mutableStateOf(user ?: ProfileData()) }

    LaunchedEffect(user) {
        data = user ?: ProfileData()
    }

    fun setData(newData: ProfileData) {
        data = newData
        data = data.copy(drinkGoalBurdenInLiters = profileModel.getDrinkGoalBurdenInLiters(data))
    }

    var currentTab by remember { mutableStateOf(0) }


    Column(modifier = Modifier.fillMaxSize()) {
        // Header - 25% height
        Box(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            StepHeader(
                data,
                currentTab,
                onClick = { currentTab = it }
            )
        }

        // Body - 60% height
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
                .background(Background),
        ) {
            StepBody(
                data = data,
                setData = {setData(it)},
                mainCurrentTab = currentTab,
                setMainCurrentTab = { currentTab = it }
            )
        }

        // Footer - 10% height
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth()
                .background(Background),
            contentAlignment = Alignment.Center
        ) {
            StepFooter(
                currentTab = currentTab,
                total = 2,
                onClick = {
                    if(currentTab < 1) {
                        currentTab++
                    } else {
                        setData(data.copy(status = 2))
                        data = data.copy(drinkGoalBurdenInLiters = profileModel.getDrinkGoalBurdenInLiters(data))
                        profileModel.updateProfileData(data)
//                        navController.navigate(Graph.MainScreenGraph) {
//                            launchSingleTop = true
//                        }
                    }
                }
            )
        }
    }
}