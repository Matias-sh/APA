package com.cocido.apa.ui.screens

// Diseño base: carrito.png (APA_png)

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.data.AppDataRepository
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

data class SavedCart(
    val name: String,
    val productCount: Int
)

@Composable
fun CartScreen(
    cartItems: Map<String, Int>,
    products: List<Product>,
    repository: AppDataRepository,
    onQuantityChange: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit,
    onConfirmPurchase: () -> Unit,
    onSaveCart: (String) -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val itemsForUi = products
        .mapNotNull { product ->
            val qty = cartItems[product.id] ?: 0
            if (qty > 0) product.copy(quantity = qty, isInCart = true) else null
        }
    
    var savedCartsData by remember { mutableStateOf<List<com.cocido.apa.data.SavedCartJson>>(emptyList()) }
    
    fun refreshSavedCarts() {
        savedCartsData = repository.getSavedCarts()
    }
    
    LaunchedEffect(Unit) {
        refreshSavedCarts()
    }

    var showSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(APAWhite)
    ) {
        // StatusBar
        StatusBar()
        
        // Header con logo centrado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            APALogo(size = LogoSize.SMALL)
        }
        
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            
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
                itemsForUi.forEach { product ->
                    CartItemCard(
                        product = product,
                        onQuantityChange = { newQty -> onQuantityChange(product.id, newQty) },
                        onRemove = { onRemoveItem(product.id) },
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
                    val totalText = itemsForUi.sumOf { it.quantity }.toString()
                    Text(
                        text = totalText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = APADarkBlue
                    )
                }
                
                if (showSuccess) {
                    Text(
                        text = "¡Compra confirmada!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = APABlue
                    )
                }
                
                APAButton(
                    text = "Confirmar Compra",
                    onClick = {
                        onConfirmPurchase()
                        showSuccess = true
                    },
                    isPrimary = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                var showSaveDialog by remember { mutableStateOf(false) }
                var cartName by remember { mutableStateOf("") }
                
                APAButton(
                    text = "Guardar Carrito",
                    onClick = { 
                        if (itemsForUi.isNotEmpty()) {
                            showSaveDialog = true
                        }
                    },
                    isPrimary = false,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (showSaveDialog) {
                    // Dialog simple para nombre del carrito
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = { showSaveDialog = false },
                        title = { Text("Guardar carrito") },
                        text = {
                            APATextField(
                                value = cartName,
                                onValueChange = { cartName = it },
                                placeholder = "Nombre del carrito",
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    if (cartName.isNotBlank()) {
                                        onSaveCart(cartName)
                                        refreshSavedCarts()
                                        cartName = ""
                                        showSaveDialog = false
                                    }
                                }
                            ) {
                                Text("Guardar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSaveDialog = false }) {
                                Text("Cancelar")
                            }
                        }
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
            if (savedCartsData.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    savedCartsData.forEach { cart ->
                        SavedCartCard(
                            cart = SavedCart(cart.name, cart.productCount),
                            onClick = { onNavigate("savedCarts") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
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
            cartItemCount = itemsForUi.sumOf { it.quantity }
        )
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
                    if (product.imageRes != null) {
                        Image(
                            painter = painterResource(id = product.imageRes),
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text(
                            text = product.name.take(1),
                            fontSize = 12.sp,
                            color = APAGray
                        )
                    }
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

