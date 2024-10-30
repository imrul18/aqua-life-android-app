package com.imrul.aqua_life.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.imrul.aqua_life.navigation.AuthScreens
import com.imrul.aqua_life.navigation.Graph
import com.imrul.aqua_life.screens.auth.Login
import com.imrul.aqua_life.screens.auth.ProfileSetup

fun NavGraphBuilder.authGraph(rootNavController: NavHostController, destination: String) {
    navigation(
        route = Graph.AuthGraph,
        startDestination = destination
    ) {
        composable(route = AuthScreens.Login.route) {
            Login(navController = rootNavController)
        }
        composable(route = AuthScreens.ProfileSetup.route) {
            ProfileSetup(navController = rootNavController)
        }
    }
}