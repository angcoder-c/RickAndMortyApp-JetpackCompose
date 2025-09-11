package com.example.app.Activities

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.app.AppNavHost
import com.example.app.ui.theme.AppTheme
import com.example.app.R
import com.example.app.Character
import com.example.app.CharacterDb
import com.example.app.RoutingNames

class MainActivity : ComponentActivity() {
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
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var characters = CharacterDb().getAllCharacters()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Characters",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Start,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // characters list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(characters) { character ->
                Card (
                    onClick = {
                        navController.navigate(RoutingNames.CharacterDetailScreen(character.id))
                    }
                ){
                    CharacterRow(character = character)
                }

            }
        }
    }
}

@Composable
fun CharacterRow(character: Character) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // consumir imaen desde el api
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            fallback = painterResource(id = R.drawable.ic_launcher_foreground)
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
        MainScreen(
            navController = rememberNavController()
        )
    }
}

// dark theme preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenDarkPreview() {
    AppTheme {
        MainScreen(
            navController = rememberNavController()
        )
    }
}
