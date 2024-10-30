package com.imrul.aqua_life.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imrul.aqua_life.dataset.ProfileDataModel
import com.imrul.aqua_life.navigation.AuthScreens
import com.imrul.aqua_life.navigation.Graph
import com.imrul.aqua_life.screens.main.MainScreen

@Composable
fun RootGraph(modifier: Modifier = Modifier) {
    val viewModel: ProfileDataModel = viewModel()
    val user by viewModel.getLastProfileData().observeAsState()

    var startDestination = Graph.AuthGraph;
    var destination = AuthScreens.Login.route

    if(user?.status == 1) {
        destination = AuthScreens.ProfileSetup.route
    }
    if(user?.status == 2) {
        startDestination = Graph.MainScreenGraph
    }

    val rootNavController = rememberNavController()
        NavHost(
            navController = rootNavController,
            route = Graph.RootGraph,
            startDestination = startDestination
        ) {
            authGraph(
                rootNavController,
                destination
            )
            composable(
                route = Graph.MainScreenGraph
            ) {
                MainScreen(rootNavHostController = rootNavController)
            }
        }
}