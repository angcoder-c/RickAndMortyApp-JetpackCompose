package com.example.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.app.Activities.CharacterDetailScreen
import com.example.app.Activities.LoginScreen
import com.example.app.Activities.MainScreen
import com.example.app.RoutingNames

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
        composable<RoutingNames.MainScreen> {
            MainScreen(navController)
        }
        composable<RoutingNames.LoginScreen> {
            LoginScreen(navController)
        }

        composable<RoutingNames.CharacterDetailScreen> { backStackEntry ->
            val characterDetail = backStackEntry.toRoute<RoutingNames.CharacterDetailScreen>()
            CharacterDetailScreen(
                characterId = characterDetail.characterId,
                navController = navController
            )
        }
    }
}