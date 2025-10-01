package com.example.app.Activities

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app.AppNavHost
import com.example.app.ui.theme.AppTheme
import com.example.app.ViewModels.CharacterDetailViewModel
import com.example.app.components.ApiImage
import com.example.app.components.HeaderBackComponent
import com.example.app.components.DataField
import com.example.app.components.ErrorViewComponent
import com.example.app.components.LoadingViewComponent

class CharacterDetailScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = viewModel()
) {
    val characterDetailState by viewModel.characterDetailScreenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderBackComponent(
            title = "Character Datail",
            onClick = {
                navController.navigateUp()
            }
        )

        when {
            characterDetailState.isLoading -> {
                LoadingViewComponent()
            }
            characterDetailState.isError -> {
                ErrorViewComponent(
                    onRetry = {
                        viewModel.loadCharacter()
                    }
                )
            }
            characterDetailState.data != null -> {
                val character = characterDetailState.data!!
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // consumir imagen desde el api
                    ApiImage(
                        uri = character.image
                    )
                    Text(
                        character.name,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Column{
                        DataField("Species:", character.species)
                        DataField("Gender:", character.gender)
                        DataField("Status:", character.status)
                    }
                }
            }
        }
    }
}

// light theme preview
@Preview(showBackground = true)
@Composable
fun DetailCharacterScreenPreview() {
    AppTheme {
        CharacterDetailScreen(
            characterId = 1,
            navController = rememberNavController()
        )
    }
}

// dark theme preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailCharacterScreenDarkPreview() {
    AppTheme {
        CharacterDetailScreen(
            characterId = 1,
            navController = rememberNavController()
        )
    }
}
