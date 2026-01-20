package com.cocido.apa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.theme.APADarkBlueAlt
import com.cocido.apa.ui.theme.APAGray
import com.cocido.apa.ui.theme.APACardBackground
import com.cocido.apa.ui.theme.APAWhite
import com.cocido.apa.ui.theme.APABlue

enum class BottomNavItem(val label: String, val route: String) {
    HOME("Inicio", "home"),
    CART("Carrito", "cart"),
    PROFILE("Yo", "profile"),
    SETTINGS("Ajustes", "settings")
}

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onItemSelected: (BottomNavItem) -> Unit,
    cartItemCount: Int = 0,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(APAWhite)
    ) {
        // Borde superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(APACardBackground)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Inicio
            BottomNavItem(
                item = BottomNavItem.HOME,
                currentRoute = currentRoute,
                icon = Icons.Default.Home,
                onItemSelected = onItemSelected,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(32.dp))
            
            // Carrito
            BottomNavItemWithBadge(
                item = BottomNavItem.CART,
                currentRoute = currentRoute,
                icon = Icons.Default.ShoppingCart,
                badgeCount = cartItemCount,
                onItemSelected = onItemSelected,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(32.dp))
            
            // Yo (Profile)
            BottomNavItem(
                item = BottomNavItem.PROFILE,
                currentRoute = currentRoute,
                icon = Icons.Default.Person,
                onItemSelected = onItemSelected,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(32.dp))
            
            // Ajustes
            BottomNavItem(
                item = BottomNavItem.SETTINGS,
                currentRoute = currentRoute,
                icon = Icons.Default.Menu,
                onItemSelected = onItemSelected,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    item: BottomNavItem,
    currentRoute: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = currentRoute == item.route
    
    Column(
        modifier = modifier
            .clickable { onItemSelected(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .then(
                    if (isSelected) {
                        Modifier.background(APADarkBlueAlt, RoundedCornerShape(8.dp))
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = item.label,
                tint = if (isSelected) APAWhite else APAGray,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Text(
            text = item.label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = if (isSelected) APADarkBlueAlt else APAGray
        )
    }
}

@Composable
private fun BottomNavItemWithBadge(
    item: BottomNavItem,
    currentRoute: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    badgeCount: Int,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = currentRoute == item.route
    
    Column(
        modifier = modifier
            .clickable { onItemSelected(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .then(
                    if (isSelected) {
                        Modifier.background(APADarkBlueAlt, RoundedCornerShape(8.dp))
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = item.label,
                tint = if (isSelected) APAWhite else APABlue,
                modifier = Modifier.size(24.dp)
            )
            
            // Badge de cantidad
            if (badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                        .size(20.dp)
                        .background(APABlue, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = badgeCount.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = APAWhite
                    )
                }
            }
        }
        
        Text(
            text = item.label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = if (isSelected) APADarkBlueAlt else APAGray
        )
    }
}
