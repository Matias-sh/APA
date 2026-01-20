package com.cocido.apa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

data class SavedCart(
    val name: String,
    val productCount: Int
)

@Composable
fun CartScreen(
    onNavigate: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cartItems = remember {
        listOf(
            Product("1", "Arroz Lucchetti 1kg", "3.99999", quantity = 1, isInCart = true),
            Product("2", "Arroz Gallo Largo 1kg", "3.99999", quantity = 8, isInCart = true),
            Product("3", "Arroz Gallo Largo 1kg", "3.99999", quantity = 2, isInCart = true)
        )
    }
    
    val savedCarts = remember {
        listOf(
            SavedCart("Compras del mes", 200),
            SavedCart("Carne", 12),
            SavedCart("Limpieza", 30)
        )
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
            
            // Headers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Producto",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAGray
                )
                Text(
                    text = "Cantidad",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAGray
                )
            }
            
            // Items del carrito
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                cartItems.forEach { product ->
                    CartItemCard(
                        product = product,
                        onQuantityChange = { },
                        onRemove = { },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Total y acciones
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APADarkGrayAlt
                    )
                    Text(
                        text = "43,999.89",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = APADarkBlue
                    )
                }
                
                APAButton(
                    text = "Confirmar Compra",
                    onClick = { },
                    isPrimary = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    APAButton(
                        text = "Guardar Carrito",
                        onClick = { onNavigate("savedCarts") },
                        isPrimary = false,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Guardar",
                        tint = APAWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Text(
                    text = "Guardar un carrito te permite repetir la misma compra a futuro para ahorrarte tiempo.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAGray
                )
            }
            
            // Carritos guardados
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                savedCarts.forEach { cart ->
                    SavedCartCard(
                        cart = cart,
                        onClick = { onNavigate("savedCarts") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        // Navegación inferior
        BottomNavigationBar(
            currentRoute = "cart",
            onItemSelected = { item ->
                when (item.route) {
                    "home" -> onNavigate("home")
                    "cart" -> onNavigate("cart")
                    "profile" -> onNavigate("profile")
                    "settings" -> onNavigate("settings")
                }
            },
            cartItemCount = 0
        )
        
        // Handle de navegación inferior
        NavigationHandle()
    }
}

@Composable
private fun CartItemCard(
    product: Product,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APACardBackground, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen del producto
                Box(
                    modifier = Modifier
                        .width(32.5.dp)
                        .height(50.387.dp)
                        .background(APAWhite, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = product.name.take(1),
                        fontSize = 12.sp,
                        color = APAGray
                    )
                }
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APABlue
                    )
                    Text(
                        text = product.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APADarkBlue
                    )
                }
            }
            
            // Controles de cantidad
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .height(44.dp)
                    .background(APAWhite, RoundedCornerShape(16.dp))
                    .border(1.dp, APALightGray, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (product.quantity > 1) onQuantityChange(product.quantity - 1) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Disminuir",
                            tint = APALightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Text(
                        text = product.quantity.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APABlue
                    )
                    
                    IconButton(
                        onClick = { onQuantityChange(product.quantity + 1) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar",
                            tint = APALightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            
            // Botón eliminar
            Box(
                modifier = Modifier
                    .offset(x = 16.dp)
                    .size(40.dp)
                    .background(APADarkBlue, RoundedCornerShape(bottomStart = 16.dp))
                    .clickable(onClick = onRemove),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = APAWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
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
                textDecoration = TextDecoration.Underline
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

@Composable
private fun NavigationHandle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(APAWhite.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(140.dp)
                .height(4.dp)
                .background(APALightGray, RoundedCornerShape(12.dp))
        )
    }
}
