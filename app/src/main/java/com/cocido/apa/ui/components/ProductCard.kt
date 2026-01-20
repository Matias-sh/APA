package com.cocido.apa.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color as ComposeColor
import coil.compose.AsyncImage
import com.cocido.apa.R
import com.cocido.apa.ui.theme.APABlue
import com.cocido.apa.ui.theme.APACardBackground
import com.cocido.apa.ui.theme.APALightGray
import com.cocido.apa.ui.theme.APADarkBlue
import com.cocido.apa.ui.theme.APAGray
import com.cocido.apa.ui.theme.APADarkGrayAlt
import com.cocido.apa.ui.theme.APARedAlt
import com.cocido.apa.ui.theme.APAWhite

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String = "",
    val imageRes: Int? = null,
    val quantity: Int = 0,
    val isInCart: Boolean = false
)

@Composable
fun ProductCard(
    product: Product,
    onProductClick: () -> Unit,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit = {},
    onQuantityChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(APACardBackground)
            .then(
                if (product.isInCart) {
                    Modifier.border(2.dp, APABlue, RoundedCornerShape(16.dp))
                } else {
                    Modifier
                }
            )
            .clickable { onProductClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    product.imageRes != null -> {
                        Image(
                            painter = painterResource(id = product.imageRes),
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    product.imageUrl.isNotEmpty() -> {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                    }
                    else -> {
                    // Placeholder si no hay imagen
                    Text(
                        text = product.name,
                        fontSize = 12.sp,
                        color = ComposeColor.Gray
                    )
                    }
                }
            }
            
            // Nombre del producto
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = if (product.isInCart) APABlue else APADarkGrayAlt,
                maxLines = 2
            )
            
            // Precio
            Text(
                text = product.price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = APADarkBlue
            )
            
            // Badge de carrito en esquina superior izquierda (si está en carrito)
            if (product.isInCart) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .offset(x = (-16).dp, y = (-16).dp)
                        .background(APABlue, RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (product.quantity > 1) {
                        Text(
                            text = product.quantity.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = APAWhite
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "En carrito",
                            tint = APAWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else {
                // Badge de agregar al carrito
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .offset(x = (-16).dp, y = (-16).dp)
                        .background(APABlue, RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                        .size(40.dp)
                        .clickable { onAddToCart() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Agregar al carrito",
                        tint = APAWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Controles de cantidad (si está en carrito)
            if (product.isInCart && product.quantity > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón eliminar
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(APARedAlt, RoundedCornerShape(8.dp))
                            .clickable { onRemoveFromCart() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = APAWhite,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    // Controles de cantidad
                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(40.dp)
                            .background(APAWhite, RoundedCornerShape(8.dp))
                            .border(1.dp, APALightGray, RoundedCornerShape(8.dp)),
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
                }
            }
        }
    }
}
