package com.imrul.aqua_life.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.imrul.aqua_life.navigation.BottomNavigationBar
import com.imrul.aqua_life.navigation.graphs.MainScreenGraph
import com.imrul.aqua_life.utils.bottomNavigationItemsList

@Composable
fun MainScreen(
    rootNavHostController: NavHostController,
    homeNavController : NavHostController = rememberNavController()
) {
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(items = bottomNavigationItemsList, currentRoute = currentRoute ){ currentNavigationItem->
                homeNavController.navigate(currentNavigationItem.route){
                    homeNavController.graph.startDestinationRoute?.let { startDestinationRoute ->
                        popUpTo(startDestinationRoute) {
                            saveState = true
                        }
                    }
                    restoreState = true
                }
            }
        }
    ) {innerPadding->
        MainScreenGraph(rootNavHostController, homeNavController, innerPadding )
    }
}