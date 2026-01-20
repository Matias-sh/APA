package com.cocido.apa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.APABlue
import com.cocido.apa.ui.theme.APACardBackground
import com.cocido.apa.ui.theme.APAGray
import com.cocido.apa.ui.theme.APALightGray

@Composable
fun RepeatPurchaseScreen(
    onProductClick: (String) -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Datos de ejemplo - producto para repetir compra
    val product = remember {
        Product("1", "Arroz Lucchetti 1kg", "3.99999")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        // Logo
        APALogo(modifier = Modifier.padding(bottom = 16.dp))
        
        // Barra de búsqueda
        SearchBar(
            searchText = "Arroz 1kg",
            onSearchTextChange = { },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Card del producto
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = APACardBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Imagen del producto
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(ComposeColor.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (product.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = product.imageUrl,
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text(
                            text = product.name,
                            fontSize = 12.sp,
                            color = ComposeColor.Gray
                        )
                    }
                    
                    // Icono de carrito
                    IconButton(
                        onClick = onAddToCart,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Agregar al carrito",
                            tint = APABlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Nombre del producto
                    Text(
                        text = product.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComposeColor.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    
                    // Precio
                    Text(
                        text = product.price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = ComposeColor.Black
                    )
                }
            }
        }
        
        // Sección "También puede interesarte"
        Text(
            text = "También puede interesarte",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = APAGray,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Chips de sugerencias
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            FilterChip(
                selected = false,
                onClick = { },
                label = { Text("Lata de verduras mixtas", fontSize = 12.sp) }
            )
            FilterChip(
                selected = false,
                onClick = { },
                label = { Text("Amanda", fontSize = 12.sp) }
            )
        }
    }
}
