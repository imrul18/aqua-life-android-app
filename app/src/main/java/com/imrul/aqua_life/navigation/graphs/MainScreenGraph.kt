package com.imrul.aqua_life.navigation.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.imrul.aqua_life.dataset.DrinkDataModel
import com.imrul.aqua_life.navigation.Graph
import com.imrul.aqua_life.navigation.MainScreenScreens
import com.imrul.aqua_life.screens.main.History
import com.imrul.aqua_life.screens.main.Home
import com.imrul.aqua_life.screens.main.Profile
import com.imrul.aqua_life.screens.main.Site
import com.imrul.aqua_life.ui.theme.Background

@Composable
fun MainScreenGraph(
    rootNavHostController: NavHostController,
    mainNavController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = mainNavController,
        route = Graph.MainScreenGraph,
        startDestination = MainScreenScreens.Home.route,
        modifier =  Modifier.background(Background),
    ) {
        composable(route = MainScreenScreens.Home.route) {
            Home(navController = rootNavHostController, innerPadding = innerPadding)
        }
        composable(route = MainScreenScreens.Site.route) {
            Site(navController = rootNavHostController, innerPadding = innerPadding)
        }
        composable(route = MainScreenScreens.Profile.route) {
            Profile(navController = rootNavHostController, innerPadding = innerPadding)
        }
        composable(route = MainScreenScreens.History.route) {
            History(navController = rootNavHostController, innerPadding = innerPadding)
        }
    }
}