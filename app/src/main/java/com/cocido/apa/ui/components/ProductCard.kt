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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.BaselineShift
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
                .fillMaxHeight()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagen del producto - más grande y mejor distribuida
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                when {
                    product.imageRes != null -> {
                        Image(
                            painter = painterResource(id = product.imageRes),
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                    product.imageUrl.isNotEmpty() -> {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
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
            
            // Nombre y precio - agrupados abajo
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Nombre del producto
                Text(
                    text = product.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (product.isInCart) APABlue else APADarkGrayAlt,
                    maxLines = 2,
                    minLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                
                // Precio con formato de superíndice (ej: "2,999⁹⁹")
                Text(
                    text = formatPriceWithSuperscript(product.price),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = APADarkBlue,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            
            // Controles de cantidad (si está en carrito)
            if (product.isInCart && product.quantity > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón eliminar
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(APARedAlt, RoundedCornerShape(8.dp))
                            .clickable { onRemoveFromCart() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = APAWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    // Controles de cantidad
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .padding(horizontal = 8.dp)
                            .background(APAWhite, RoundedCornerShape(8.dp))
                            .border(1.dp, APALightGray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { if (product.quantity > 1) onQuantityChange(product.quantity - 1) },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Disminuir",
                                    tint = APALightGray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            
                            Text(
                                text = product.quantity.toString(),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = APABlue,
                                maxLines = 1
                            )
                            
                            IconButton(
                                onClick = { onQuantityChange(product.quantity + 1) },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Aumentar",
                                    tint = APALightGray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Badge de carrito en esquina superior izquierda (posicionado absolutamente)
        if (product.isInCart) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 4.dp, y = 4.dp)
                    .size(44.dp)
                    .background(APABlue, RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Siempre mostrar el número si está en carrito (según Figma)
                // Usar Box adicional para asegurar centrado perfecto
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = product.quantity.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = APAWhite,
                        maxLines = 1,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            // Badge de agregar al carrito
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 4.dp, y = 4.dp)
                    .size(44.dp)
                    .background(APABlue, RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                    .clickable { onAddToCart() },
                contentAlignment = Alignment.Center
            ) {
                // Usar Box adicional para asegurar centrado perfecto del icono
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Agregar al carrito",
                        tint = APAWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Formatea el precio con superíndice para los últimos dos dígitos después de la coma/punto
 * Ejemplo: "2,99999" -> "2,999⁹⁹" o "3.99999" -> "3.999⁹⁹"
 */
private fun formatPriceWithSuperscript(price: String): androidx.compose.ui.text.AnnotatedString {
    return buildAnnotatedString {
        // Normalizar: convertir punto a coma para consistencia
        val normalizedPrice = price.replace('.', ',')
        
        // Buscar la coma como separador decimal
        val separatorIndex = normalizedPrice.indexOf(',')
        
        if (separatorIndex >= 0 && normalizedPrice.length > separatorIndex + 2) {
            // Agregar la parte antes de los últimos 2 dígitos
            append(normalizedPrice.substring(0, normalizedPrice.length - 2))
            
            // Agregar los últimos 2 dígitos con superíndice
            withStyle(
                style = androidx.compose.ui.text.SpanStyle(
                    fontSize = 10.sp,
                    baselineShift = BaselineShift.Superscript
                )
            ) {
                append(normalizedPrice.substring(normalizedPrice.length - 2))
            }
        } else {
            // Si no hay formato esperado, devolver el precio tal cual
            append(price)
        }
    }
}
