package com.cocido.apa.ui.screens

// Diseño base: carrito-2.png (APA_png) - pestaña \"Yo\"

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun ProfileScreen(
    cartItemCount: Int = 0,
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Profile Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(APABlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JD",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = APAWhite
                    )
                }
                
                // User Info
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Juan Díaz",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = APADarkBlue
                    )
                    Text(
                        text = "juan.diaz@email.com",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APAGray
                    )
                }
                
                // Points Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(APABlue, APADarkBlue)
                            ),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Puntos APA",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = APAWhite,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "2,450",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = APAWhite,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "¡Canjea tus puntos por descuentos!",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = APAWhite.copy(alpha = 0.9f),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            // Menu Options
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Mi Cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = APADarkBlue
                )
                
                MenuOptionCard(
                    text = "Mis Pedidos",
                    onClick = { }
                )
                
                MenuOptionCard(
                    text = "Carritos Guardados",
                    onClick = { onNavigate("savedCarts") }
                )
                
                MenuOptionCard(
                    text = "Mis Cupones",
                    icon = Icons.Default.LocalOffer,
                    onClick = { }
                )
                
                MenuOptionCard(
                    text = "Mis Direcciones",
                    onClick = { }
                )
                
                MenuOptionCard(
                    text = "Métodos de Pago",
                    onClick = { }
                )
                
                // Logout Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(APAWhite, RoundedCornerShape(16.dp))
                        .border(1.dp, APARed, RoundedCornerShape(16.dp))
                        .clickable(onClick = onLogout)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = APARed,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Cerrar Sesión",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = APARed
                        )
                    }
                }
            }
        }
        
        // Navegación inferior
        BottomNavigationBar(
            currentRoute = "profile",
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
private fun MenuOptionCard(
    text: String,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APACardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, APALightGray, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = APADarkBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APADarkBlue
                )
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = APABlue,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Profile Screen")
@Composable
private fun ProfileScreenPreview() {
    APATheme {
        ProfileScreen(
            cartItemCount = 3,
            onNavigate = {},
            onLogout = {}
        )
    }
}

