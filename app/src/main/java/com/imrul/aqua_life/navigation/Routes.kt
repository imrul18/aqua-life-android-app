package com.imrul.aqua_life.navigation

object Graph {
    const val RootGraph = "root_graph"

    const val AuthGraph = "auth_graph"
    const val MainScreenGraph = "main_root_graph"
}

sealed class AuthScreens(var route: String) {
    object Login : AuthScreens("login")
    object ProfileSetup : AuthScreens("profile_setup")
}

sealed class MainScreenScreens(var route: String) {
    object Home : MainScreenScreens("home")
    object Site : MainScreenScreens("site")
    object History : MainScreenScreens("history")
    object Profile : MainScreenScreens("profile")

}
