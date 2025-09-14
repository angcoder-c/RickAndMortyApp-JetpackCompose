package com.example.app.Activities

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.app.AppNavHost
import com.example.app.ui.theme.AppTheme
import com.example.app.Location
import com.example.app.LocationDb
import com.example.app.components.ApiImage
import com.example.app.components.HeaderBackComponent
import com.example.app.components.DataField

class LocationsDetailScreenActivity : ComponentActivity() {
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
fun LocationsDetailScreen (
    locationsId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var locations = LocationDb().getLocationById(locationsId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderBackComponent(
            title = "Location Datail",
            onClick = {
                navController.navigateUp()
            }
        )
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                locations.name,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Start,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            )
            Column{
                DataField("ID:", locations.id.toString())
                DataField("Type:", locations.type)
                DataField("Dimension:", locations.dimension)
            }
        }
    }
}

// light theme preview
@Preview(showBackground = true)
@Composable
fun LocationsDetailScreenPreview() {
    AppTheme {
        LocationsDetailScreen(
            locationsId = 1,
            navController = rememberNavController()
        )
    }
}

// dark theme preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LocationsDetailScreenDarkPreview() {
    AppTheme {
        LocationsDetailScreen(
            locationsId = 1,
            navController = rememberNavController()
        )
    }
}