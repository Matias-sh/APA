package com.cocido.apa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onAddToCart: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                // Barra de búsqueda
                SearchBar(
                    searchText = "",
                    onSearchTextChange = {},
                    onClick = { onNavigate("search") },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            item {
                // Banner promocional "Descuentos en carnes"
                PromotionalBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(242.dp)
                        .padding(bottom = 16.dp)
                )
            }
            
            item {
                // Sección "Haces las compras del mes?"
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Haces las compras del mes?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APAGray
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        APAButton(
                            text = "Repetir útlima compra",
                            onClick = { },
                            isPrimary = false,
                            modifier = Modifier.weight(1f)
                        )
                        
                        APAButton(
                            text = "Ver carritos guardados",
                            onClick = { onNavigate("savedCarts") },
                            isPrimary = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            item {
                // Grid de categorías 2x4
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .padding(bottom = 10.dp)
                ) {
                    // OFERTAS DEL FINDE
                    item {
                        CategoryCard(
                            title = "OFERTAS\nDEL\nFINDE",
                            subtitle = "del 16 al 18 de enero",
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Frutas y Verduras
                    item {
                        CategoryCard(
                            title = "Frutas y Verduras",
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Bebidas
                    item {
                        CategoryCard(
                            title = "Bebidas",
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Postres
                    item {
                        CategoryCard(
                            title = "Postres",
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Banner de Higiene (span 2 columnas)
                    item(span = { GridItemSpan(2) }) {
                        HygieneBanner(
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Limpieza
                    item {
                        CategoryCard(
                            title = "Limpieza",
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Congelados
                    item {
                        CategoryCard(
                            title = "Congelados",
                            onClick = { onNavigate("search") }
                        )
                    }
                }
            }
        }
        
        // Navegación inferior
        BottomNavigationBar(
            currentRoute = "home",
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
private fun PromotionalBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(APADarkBlue, APABlue)
                ),
                RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "DESCUENTOS",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APAWhite
                )
                Text(
                    text = "EN CARNES",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APAWhite
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "DE HASTA",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APAWhite,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "EL 25%",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APAWhite,
                    textAlign = TextAlign.Right
                )
            }
        }
        
        // Indicadores de página (dots)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(23.dp)
                    .height(6.dp)
                    .background(APALightGray, RoundedCornerShape(22.dp))
            )
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(6.dp)
                    .background(APABlue, RoundedCornerShape(44.dp))
            )
            Box(
                modifier = Modifier
                    .width(23.dp)
                    .height(6.dp)
                    .background(APALightGray, RoundedCornerShape(22.dp))
            )
        }
    }
}

@Composable
private fun CategoryCard(
    title: String,
    subtitle: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APACardBackground, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(APAWhite, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder para imagen de categoría
                Text(
                    text = title.take(1),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = APABlue
                )
            }
            
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = APADarkBlue,
                textAlign = TextAlign.Center
            )
            
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAGray
                )
            }
        }
    }
}

@Composable
private fun HygieneBanner(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APACardBackground, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen placeholder
            Box(
                modifier = Modifier
                    .width(74.dp)
                    .height(111.dp)
                    .background(APAWhite, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🧴",
                    fontSize = 48.sp
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "50% DE DESCUENTO",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APABlue,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "EN LA 2DA UNIDAD",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APABlue,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "DE HIGIENE",
                    fontSize = 28.8.sp,
                    fontWeight = FontWeight.Bold,
                    color = APABlue,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "en productos seleccionados*",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAWhite
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
