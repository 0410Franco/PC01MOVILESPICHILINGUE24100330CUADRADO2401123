package com.example.uesanapp.navigation

sealed class Routes(val route: String) {
    object MainMenu : Routes("main_menu")
    object Luggage : Routes("luggage")
    object Budget : Routes("budget")
    object Destinations : Routes("destinations")
    object Permissions : Routes("permissions")
}
