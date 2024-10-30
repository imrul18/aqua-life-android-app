package com.imrul.aqua_life.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import com.imrul.aqua_life.R
import com.imrul.aqua_life.navigation.MainScreenScreens
import com.imrul.aqua_life.navigation.NavigationItem

val bottomNavigationItemsList = listOf(
        NavigationItem(
            route = MainScreenScreens.Home.route,
            selectedIcon = R.drawable.home_selected,
            unSelectedIcon = R.drawable.home_unselected
        ),
        NavigationItem(
            route = MainScreenScreens.Site.route,
            selectedIcon = R.drawable.website_selected,
            unSelectedIcon = R.drawable.website_unselected
        ),
        NavigationItem(
            route = MainScreenScreens.History.route,
            selectedIcon = R.drawable.history_selected,
            unSelectedIcon = R.drawable.history_unselected
        ),
        NavigationItem(
            route = MainScreenScreens.Profile.route,
            selectedIcon = R.drawable.person_selected,
            unSelectedIcon = R.drawable.person_unselected
        ),
    )