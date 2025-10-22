package com.example.app.Activities

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.AppNavHost
import com.example.app.ui.theme.AppTheme
import com.example.app.Character
import com.example.app.RoutingNames
import com.example.app.components.ApiImage
import com.example.app.components.BottomNavigationBar
import com.example.app.components.HeaderComponent
import com.example.app.ViewModels.CharactersViewModel
import com.example.app.components.ErrorViewComponent
import com.example.app.components.LoadingViewComponent
import com.example.app.database.entities.CharacterEntity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val screensWithNavBar = listOf(
                    "MainScreen",
                    "LocationsScreen",
                    "ProfileScreen"
                )

                // verificar si debemos mostrar el navbar
                val showBottomBar = screensWithNavBar.any { screen ->
                    currentRoute?.contains(screen) == true
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = viewModel()
) {
    val activity = LocalContext.current as? Activity
    val charactersState by viewModel.charactersScreenState.collectAsState()

    // manejar el boton de back
    BackHandler {
        activity?.finish()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderComponent("Characters")

        when {
            charactersState.isLoading -> {
                LoadingViewComponent()
            }

            charactersState.isError -> {
                ErrorViewComponent(
                    onRetry = { viewModel.loadCharacters() }
                )
            }

            else -> {
                // characters list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(charactersState.data) { character ->
                        Card(
                            onClick = {
                                navController.navigate(RoutingNames.CharacterDetailScreen(character.id))
                            }
                        ) {
                            CharacterRow(character = character)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterRow(character: CharacterEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ApiImage(
            uri = character.image,
            size = 32
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${character.species} - ${character.status}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

// light theme preview
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(navController = rememberNavController())
    }
}

// dark theme preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenDarkPreview() {
    AppTheme {
        MainScreen(navController = rememberNavController())
    }
}