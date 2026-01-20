package com.cocido.apa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cocido.apa.navigation.AppNavigation
import com.cocido.apa.ui.components.BottomNavItem
import com.cocido.apa.ui.components.BottomNavigationBar
import com.cocido.apa.ui.theme.APATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APATheme {
                val navController = rememberNavController()
                var currentRoute by remember { mutableStateOf("login") }
                var cartItemCount by remember { mutableStateOf(1) }
                
                // Observar cambios en la ruta
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                currentRoute = navBackStackEntry?.destination?.route ?: "login"
                
                AppNavigation(
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}