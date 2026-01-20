package com.cocido.apa.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cocido.apa.data.AppDataRepository
import com.cocido.apa.ui.components.BottomNavItem
import com.cocido.apa.ui.components.Product
import com.cocido.apa.ui.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    repository: AppDataRepository,
    modifier: Modifier = Modifier,
    startDestination: String = "login"
) {
    // Catálogo desde el repositorio
    var catalog by remember { mutableStateOf<List<Product>>(emptyList()) }
    
    LaunchedEffect(Unit) {
        catalog = repository.getProducts()
    }

    // Estado global de carrito: productId -> cantidad
    var cartItems by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    fun addToCart(productId: String, quantity: Int = 1) {
        val current = cartItems[productId] ?: 0
        val newQty = (current + quantity).coerceAtLeast(1)
        cartItems = cartItems.toMutableMap().apply { this[productId] = newQty }
    }

    fun updateCartQuantity(productId: String, quantity: Int) {
        val clamped = quantity.coerceAtLeast(1)
        cartItems = cartItems.toMutableMap().apply { this[productId] = clamped }
    }

    fun removeFromCart(productId: String) {
        cartItems = cartItems.toMutableMap().apply { remove(productId) }
    }

    val cartCount = cartItems.values.sum()

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
                cartItemCount = cartCount,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddToCart = { productId ->
                    addToCart(productId)
                },
                onRepeatLastPurchase = {
                    val lastPurchase = repository.getLastPurchase()
                    lastPurchase.forEach { (productId, quantity) ->
                        cartItems = cartItems.toMutableMap().apply { 
                            this[productId] = quantity 
                        }
                    }
                    navController.navigate("cart")
                }
            )
        }
        
        composable("search") {
            val cartForSearch = cartItems
            SearchScreen(
                products = catalog,
                cartItems = cartForSearch,
                 cartItemCount = cartCount,
                onUpdateQuantity = { productId, quantity ->
                    updateCartQuantity(productId, quantity)
                },
                onRemoveFromCart = { productId ->
                    removeFromCart(productId)
                },
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("search") { inclusive = false }
                    }
                },
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddToCart = { productId ->
                    addToCart(productId)
                },
                modifier = modifier
            )
        }
        
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = catalog.find { it.id == productId }
            ProductDetailScreen(
                product = product,
                productId = productId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToCart = { quantity ->
                    addToCart(productId, quantity)
                },
                onNavigateToCart = {
                    navController.navigate("cart") {
                        popUpTo("product_detail/$productId") { inclusive = true }
                    }
                }
            )
        }
        
        composable("cart") {
            CartScreen(
                cartItems = cartItems,
                products = catalog,
                repository = repository,
                onQuantityChange = { productId, quantity ->
                    updateCartQuantity(productId, quantity)
                },
                onRemoveItem = { productId ->
                    removeFromCart(productId)
                },
                onConfirmPurchase = {
                    // Guardar como última compra antes de limpiar
                    repository.saveLastPurchase(cartItems)
                    cartItems = emptyMap()
                },
                onSaveCart = { name ->
                    repository.saveCart(name, cartItems)
                },
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
                cartItemCount = cartCount,
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
                cartItemCount = cartCount,
                repository = repository,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("savedCarts") { inclusive = false }
                    }
                },
                onLoadCart = { cartId ->
                    val savedItems = repository.loadSavedCart(cartId)
                    savedItems.forEach { (productId, quantity) ->
                        cartItems = cartItems.toMutableMap().apply {
                            this[productId] = quantity
                        }
                    }
                    navController.navigate("cart")
                }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                cartItemCount = cartCount,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("settings") { inclusive = false }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("repeat_purchase") {
            RepeatPurchaseScreen(
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddToCart = {
                    addToCart("1")
                }
            )
        }
    }
}
