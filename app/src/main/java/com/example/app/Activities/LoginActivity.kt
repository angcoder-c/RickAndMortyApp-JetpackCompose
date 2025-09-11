package com.example.app.Activities

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.app.R
import com.example.app.ui.theme.AppTheme
import com.example.app.AppNavHost
import com.example.app.RoutingNames

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // rick and mory logo
            Image(
                imageVector = ImageVector
                    .vectorResource(
                        id = R.drawable.rick_and_morty_logo
                    ),
                contentDescription = "rick and morty logo",
            )
            // login button
            Button(
                onClick = {
                    navController.navigate(RoutingNames.MainScreen) {
                        popUpTo(RoutingNames.LoginScreen) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Login")
            }
        }

        // footer
        Text(
            "Angel Gabriel Chavez Otzoy #24248",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// ligth theme preview
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}

// darck theme preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginDarkPreview() {
    AppTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}