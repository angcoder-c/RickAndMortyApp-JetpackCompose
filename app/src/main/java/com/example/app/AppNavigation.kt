package com.example.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.app.Activities.CharacterDetailScreen
import com.example.app.Activities.LocationsDetailScreen
import com.example.app.Activities.LocationsScreen
import com.example.app.Activities.LoginScreen
import com.example.app.Activities.MainScreen
import com.example.app.Activities.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RoutingNames.LoginScreen,
        modifier = modifier
    ) {
        // login Screen
        composable<RoutingNames.LoginScreen> {
            LoginScreen(navController = navController)
        }

        // main Screen
        composable<RoutingNames.MainScreen> {
            MainScreen(navController = navController)
        }

        // character detail screen
        composable<RoutingNames.CharacterDetailScreen> { backStackEntry ->
            val characterDetail = backStackEntry.toRoute<RoutingNames.CharacterDetailScreen>()
            CharacterDetailScreen(
                characterId = characterDetail.characterId,
                navController = navController
            )
        }

        // locations screen
        composable<RoutingNames.LocationsScreen> {
            LocationsScreen(navController = navController)
        }

        // location detail screen
        composable<RoutingNames.LocationsDetailScreen> { backStackEntry ->
            val locationDetail = backStackEntry.toRoute<RoutingNames.LocationsDetailScreen>()
            LocationsDetailScreen(
                locationsId = locationDetail.locationsId,
                navController = navController
            )
        }

        // profile screen
        composable<RoutingNames.ProfileScreen> {
            ProfileScreen(navController = navController)
        }
    }
}