package com.cocido.apa.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cocido.apa.ui.components.BottomNavItem
import com.cocido.apa.ui.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "login"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }
        
        composable("register") {
            RegisterScreen(
                onRegisterClick = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddToCart = { productId ->
                    // Lógica para agregar al carrito
                }
            )
        }
        
        composable("search") {
            SearchScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("search") { inclusive = false }
                    }
                },
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddToCart = { productId ->
                    // Lógica para agregar al carrito
                }
            )
        }
        
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToCart = {
                    // Lógica para agregar al carrito
                }
            )
        }
        
        composable("cart") {
            CartScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("cart") { inclusive = false }
                    }
                },
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("profile") { inclusive = false }
                    }
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable("savedCarts") {
            SavedCartsScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("savedCarts") { inclusive = false }
                    }
                }
            )
        }
        
        composable("settings") {
            // Placeholder para pantalla de ajustes
            Text("Ajustes")
        }
        
        composable("repeat_purchase") {
            RepeatPurchaseScreen(
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddToCart = {
                    // Lógica para agregar al carrito
                }
            )
        }
    }
}
