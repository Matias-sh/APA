package com.cocido.apa.ui.screens

// Diseño base: screen-4.png (APA_png)

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun SearchScreen(
    products: List<Product>,
    cartItems: Map<String, Int>,
    onUpdateQuantity: (String, Int) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    cartItemCount: Int = 0,
    onNavigate: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onAddToCart: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("Arroz 1kg") }

    val uiProducts = products.map { base ->
        val qty = cartItems[base.id] ?: 0
        if (qty > 0) base.copy(quantity = qty, isInCart = true) else base.copy(quantity = 0, isInCart = false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(APAWhite)
    ) {
        // StatusBar
        StatusBar()
        
        // Contenido principal
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            
            item {
                // Logo pequeño
                APALogo(
                    size = LogoSize.SMALL,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            
            item {
                // Barra de búsqueda con filtros
                SearchBar(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    onFilterClick = { },
                    showFilters = true,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            item {
                // Grid de productos 2 columnas
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(700.dp)
                        .padding(bottom = 16.dp)
                ) {
                    items(
                        count = uiProducts.size,
                        key = { index -> uiProducts[index].id }
                    ) { index ->
                        val product = uiProducts[index]
                        ProductCard(
                            product = product,
                            onProductClick = { onProductClick(product.id) },
                            onAddToCart = {
                                onAddToCart(product.id)
                            },
                            onRemoveFromCart = {
                                onRemoveFromCart(product.id)
                            },
                            onQuantityChange = { newQty ->
                                onUpdateQuantity(product.id, newQty)
                            },
                            modifier = Modifier.height(218.dp)
                        )
                    }
                }
            }
            
            item {
                // Sección "También puede interesarte"
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "También puede interesarte",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APAGray
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SuggestionChip(
                            text = "Latas de verdura",
                            onClick = { }
                        )
                        SuggestionChip(
                            text = "Amanda",
                            onClick = { }
                        )
                        SuggestionChip(
                            text = "Carne",
                            onClick = { }
                        )
                        
                        // Botón flecha
                        Box(
                            modifier = Modifier
                                .height(44.dp)
                                .width(44.dp)
                                .background(APACardBackground, RoundedCornerShape(16.dp))
                                .border(1.dp, APALightGray, RoundedCornerShape(16.dp))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Ver más",
                                tint = APALightGray,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
        
        // Navegación inferior
        BottomNavigationBar(
            currentRoute = "search",
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
private fun SuggestionChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .background(APACardBackground, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = APAGray
        )
    }
}

