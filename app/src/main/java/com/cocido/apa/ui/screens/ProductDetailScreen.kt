package com.cocido.apa.ui.screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun ProductDetailScreen(
    product: Product?,
    productId: String,
    onBackClick: () -> Unit,
    onAddToCart: (Int) -> Unit,
    onNavigateToCart: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableStateOf(1) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    
    val productData = product ?: Product(productId, "Producto", "0")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(APAWhite)
    ) {
        // StatusBar
        StatusBar()
        
        // Header con botón de volver
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = APADarkBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            APALogo(size = LogoSize.SMALL)
            
            // Espacio para centrar el logo
            Spacer(modifier = Modifier.size(40.dp))
        }
        
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Barra de búsqueda
            SearchBar(
                searchText = "",
                onSearchTextChange = { },
                onClick = { },
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Card del producto - Diseño mejorado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = APACardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen del producto - más grande y centrada
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(APAWhite)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            productData.imageRes != null -> {
                                Image(
                                    painter = painterResource(id = productData.imageRes),
                                    contentDescription = productData.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            productData.imageUrl.isNotEmpty() -> {
                                AsyncImage(
                                    model = productData.imageUrl,
                                    contentDescription = productData.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            else -> {
                                Text(
                                    text = productData.name,
                                    fontSize = 14.sp,
                                    color = APAGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Nombre del producto
                    Text(
                        text = productData.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = APADarkBlue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    // Precio unitario
                    Text(
                        text = productData.price,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = APABlue,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    // Selector de unidades
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Cantidad",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = APADarkGrayAlt,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        // Controles de cantidad - mejorados
                        Row(
                            modifier = Modifier
                                .background(APAWhite, RoundedCornerShape(16.dp))
                                .border(1.dp, APALightGray, RoundedCornerShape(16.dp))
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Reducir",
                                    tint = if (quantity > 1) APABlue else APALightGray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            Text(
                                text = quantity.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = APADarkBlue,
                                modifier = Modifier.width(40.dp),
                                textAlign = TextAlign.Center
                            )
                            
                            IconButton(
                                onClick = { quantity++ },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Aumentar",
                                    tint = APABlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        
                        // Total calculado
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Total: ${productData.price} x $quantity",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = APAGray
                        )
                    }
                }
            }
            
            // Botón principal: Agregar al carrito
            APAButton(
                text = "Agregar al carrito",
                onClick = {
                    onAddToCart(quantity)
                    showSuccessMessage = true
                },
                isPrimary = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            
            // Mensaje de éxito
            if (showSuccessMessage) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = APABlue.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✓ Producto agregado al carrito",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = APABlue
                        )
                        
                        TextButton(
                            onClick = {
                                showSuccessMessage = false
                                onNavigateToCart()
                            }
                        ) {
                            Text(
                                text = "Ver carrito",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = APABlue
                            )
                        }
                    }
                }
            }
            
            // Sección "También puede interesarte"
            Text(
                text = "También puede interesarte",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = APADarkBlue,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            
            // Chips de sugerencias
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("Lata de verduras mixtas", fontSize = 14.sp) }
                )
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("Amanda", fontSize = 14.sp) }
                )
            }
        }
    }
}
