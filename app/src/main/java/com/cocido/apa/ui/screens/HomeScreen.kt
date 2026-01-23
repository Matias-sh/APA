package com.cocido.apa.ui.screens

// Diseño base: screen-3.png (APA_png)

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun HomeScreen(
    cartItemCount: Int = 0,
    onNavigate: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onAddToCart: (String) -> Unit = {},
    onRepeatLastPurchase: () -> Unit = {},
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
                // Logo centrado en el header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    APALogo(size = LogoSize.SMALL)
                }
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
                            onClick = onRepeatLastPurchase,
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
                            imageRes = com.cocido.apa.R.drawable.ofertas_del_finde,
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Frutas y Verduras
                    item {
                        CategoryCard(
                            title = "Frutas y Verduras",
                            imageRes = com.cocido.apa.R.drawable.frutas_verduras,
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Bebidas
                    item {
                        CategoryCard(
                            title = "Bebidas",
                            imageRes = com.cocido.apa.R.drawable.bebidas,
                            onClick = { onNavigate("search") }
                        )
                    }
                    
                    // Postres
                    item {
                        CategoryCard(
                            title = "Postres",
                            imageRes = com.cocido.apa.R.drawable.postres,
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
                            imageRes = com.cocido.apa.R.drawable.limpieza,
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
            cartItemCount = cartItemCount
        )
    }
}

@Composable
private fun PromotionalBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(ComposeColor.Transparent, RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = com.cocido.apa.R.drawable.descuentos_en_carnes),
            contentDescription = "Descuentos en carnes",
            modifier = Modifier
                .fillMaxSize()
                .background(ComposeColor.Transparent, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

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
    imageRes: Int? = null,
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
                if (imageRes != null) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // Fallback: letra inicial
                    Text(
                        text = title.take(1),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = APABlue
                    )
                }
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

@Preview(showBackground = true, name = "Home Screen")
@Composable
private fun HomeScreenPreview() {
    APATheme {
        HomeScreen(
            cartItemCount = 3,
            onNavigate = {},
            onProductClick = {},
            onAddToCart = {},
            onRepeatLastPurchase = {}
        )
    }
}

