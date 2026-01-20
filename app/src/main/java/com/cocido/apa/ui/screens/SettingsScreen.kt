package com.cocido.apa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun SettingsScreen(
    cartItemCount: Int = 0,
    onNavigate: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
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
        
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            
            // Título
            Text(
                text = "Ajustes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = APADarkBlue,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Sección: Cuenta
            SettingsSection(
                title = "Cuenta",
                items = listOf(
                    SettingsItem(text = "Mi Perfil", icon = Icons.Default.Person),
                    SettingsItem(text = "Direcciones", icon = Icons.Default.LocationOn),
                    SettingsItem(text = "Métodos de Pago", icon = Icons.Default.Payment),
                    SettingsItem(text = "Historial de Pedidos", icon = Icons.Default.History)
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sección: Notificaciones
            SettingsSection(
                title = "Notificaciones",
                items = listOf(
                    SettingsItem(text = "Notificaciones Push", icon = Icons.Default.Notifications, hasSwitch = true),
                    SettingsItem(text = "Ofertas y Promociones", icon = Icons.Default.LocalOffer, hasSwitch = true),
                    SettingsItem(text = "Estado de Pedidos", icon = Icons.Default.ShoppingCart, hasSwitch = true)
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sección: Preferencias
            SettingsSection(
                title = "Preferencias",
                items = listOf(
                    SettingsItem(text = "Idioma", icon = Icons.Default.Language),
                    SettingsItem(text = "Moneda", icon = Icons.Default.AttachMoney),
                    SettingsItem(text = "Unidades de Medida", icon = Icons.Default.Info)
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sección: Ayuda
            SettingsSection(
                title = "Ayuda",
                items = listOf(
                    SettingsItem(text = "Preguntas Frecuentes", icon = Icons.Default.Help),
                    SettingsItem(text = "Contactar Soporte", icon = Icons.Default.Person),
                    SettingsItem(text = "Términos y Condiciones", icon = Icons.Default.Info),
                    SettingsItem(text = "Política de Privacidad", icon = Icons.Default.Info)
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sección: Acerca de
            SettingsSection(
                title = "Acerca de",
                items = listOf(
                    SettingsItem(text = "Versión de la App", icon = Icons.Default.Info, showValue = "1.0.0"),
                    SettingsItem(text = "Calificar App", icon = Icons.Default.Star)
                )
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
        
        // Navegación inferior
        BottomNavigationBar(
            currentRoute = "settings",
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
private fun SettingsSection(
    title: String,
    items: List<SettingsItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = APADarkBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        items.forEach { item ->
            SettingsOptionCard(
                text = item.text,
                icon = item.icon,
                onClick = item.onClick,
                hasSwitch = item.hasSwitch,
                showValue = item.showValue
            )
        }
    }
}

data class SettingsItem(
    val text: String,
    val icon: ImageVector,
    val onClick: () -> Unit = {},
    val hasSwitch: Boolean = false,
    val showValue: String? = null
)

@Composable
private fun SettingsOptionCard(
    text: String,
    icon: ImageVector? = null,
    onClick: () -> Unit = {},
    hasSwitch: Boolean = false,
    showValue: String? = null,
    modifier: Modifier = Modifier
) {
    var switchState by remember { mutableStateOf(true) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APACardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, APALightGray, RoundedCornerShape(16.dp))
            .then(if (!hasSwitch) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = APADarkBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column {
                    Text(
                        text = text,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = APADarkBlue
                    )
                    if (showValue != null) {
                        Text(
                            text = showValue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = APAGray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
            
            when {
                hasSwitch -> {
                    Switch(
                        checked = switchState,
                        onCheckedChange = { switchState = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = APAWhite,
                            checkedTrackColor = APABlue,
                            uncheckedThumbColor = APAWhite,
                            uncheckedTrackColor = APALightGray
                        )
                    )
                }
                showValue == null -> {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = APABlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
