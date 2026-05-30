    package com.example.uesanapp.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.example.uesanapp.presentation.Menu.MainMenuScreen
    import com.example.uesanapp.presentation.Equipaje.LuggageScreen
    import com.example.uesanapp.presentation.Presupuesto.BudgetScreen
    import com.example.uesanapp.presentation.Destinos.DestinationScreen
    import com.example.uesanapp.presentation.Permisos.LocationPermissionScreen

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.MainMenu.route
        ) {
            composable(Routes.MainMenu.route) {
                MainMenuScreen(navController)
            }
            composable(Routes.Luggage.route) {
                LuggageScreen(navController)
            }
            composable(Routes.Budget.route) {
                BudgetScreen(navController)
            }
            composable(Routes.Destinations.route) {
                DestinationScreen(navController)
            }
            composable(Routes.Permissions.route) {
                LocationPermissionScreen(navController)
            }
        }
    }
//...