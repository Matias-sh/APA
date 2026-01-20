package com.cocido.apa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cocido.apa.data.AppDataRepository
import com.cocido.apa.navigation.AppNavigation
import com.cocido.apa.ui.theme.APATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APATheme {
                val navController = rememberNavController()
                var currentRoute by remember { mutableStateOf("login") }
                
                // Observar cambios en la ruta
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                currentRoute = navBackStackEntry?.destination?.route ?: "login"
                
                AppNavigationContent(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                )
            }
        }
    }
}

@Composable
fun AppNavigationContent(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = remember { AppDataRepository(context) }
    
    AppNavigation(
        navController = navController,
        repository = repository,
        modifier = modifier
    )
}