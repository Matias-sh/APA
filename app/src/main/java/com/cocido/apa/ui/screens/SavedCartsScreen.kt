package com.cocido.apa.ui.screens

// Diseño base: carrito-1.png (APA_png)

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.data.AppDataRepository
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun SavedCartsScreen(
    cartItemCount: Int = 0,
    repository: AppDataRepository,
    onNavigate: (String) -> Unit = {},
    onLoadCart: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var savedCartsData by remember { mutableStateOf<List<com.cocido.apa.data.SavedCartJson>>(emptyList()) }
    
    LaunchedEffect(Unit) {
        savedCartsData = repository.getSavedCarts()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(APAWhite)
    ) {
        // StatusBar
        StatusBar()
        
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            
            // Logo pequeño
            APALogo(
                size = LogoSize.SMALL,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            
            // Page Title
            Text(
                text = "Carritos Guardados",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = APADarkBlue,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            
            // Saved Carts List
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                savedCartsData.forEach { cart ->
                    SavedCartCard(
                        cart = SavedCart(cart.name, cart.productCount),
                        onClick = { onLoadCart(cart.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // New Cart Button
            APAButton(
                text = "Crear nuevo carrito",
                onClick = { onNavigate("search") },
                isPrimary = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
        
        // Navegación inferior
        BottomNavigationBar(
            currentRoute = "savedCarts",
            onItemSelected = { item ->
                when (item.route) {
                    "home" -> onNavigate("home")
                    "cart" -> onNavigate("cart")
                    "profile" -> onNavigate("profile")
                    "settings" -> onNavigate("settings")
                }
            },
            cartItemCount = cartItemCount
        )
    }
}

@Composable
private fun SavedCartCard(
    cart: SavedCart,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APACardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, APALightGray, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = cart.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = APADarkGrayAlt
            )
            Text(
                text = "${cart.productCount} productos",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = APAGray
            )
            Text(
                text = "Ver detalle",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = APADarkBlue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = onClick)
            )
            
            // Badge de bookmark
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .offset(x = 16.dp, y = (-16).dp)
                    .size(40.dp)
                    .background(APABlue, RoundedCornerShape(bottomStart = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Guardado",
                    tint = APAWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


