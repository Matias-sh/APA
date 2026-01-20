package com.cocido.apa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.theme.APADarkGray
import com.cocido.apa.ui.theme.APABatteryGray
import com.cocido.apa.ui.theme.APACameraCutout
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    val currentTime = remember { 
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hora
            Text(
                text = currentTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = APADarkGray,
                letterSpacing = 0.14.sp
            )
            
            // Camera Cutout (placeholder - en Android real esto se maneja con el sistema)
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(APACameraCutout)
            )
            
            // Iconos de estado (señal, wifi, batería)
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Señal
                SignalIcon()
                // Wifi
                WifiIcon()
                // Batería
                BatteryIcon()
            }
        }
    }
}

@Composable
private fun SignalIcon() {
    Box(
        modifier = Modifier.size(16.dp)
    ) {
        // Icono de señal simplificado - en producción usar icono vectorial real
        Text(
            text = "📶",
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun WifiIcon() {
    Box(
        modifier = Modifier.size(16.dp)
    ) {
        // Icono de wifi simplificado - en producción usar icono vectorial real
        Text(
            text = "📶",
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun BatteryIcon() {
    Box(
        modifier = Modifier
            .width(24.dp)
            .height(16.dp)
    ) {
        // Batería baja - en producción usar icono vectorial real
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            // Borde de batería
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .background(APADarkGray.copy(alpha = 0.3f))
            )
            // Nivel de batería (bajo)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(11.dp)
                    .padding(start = 2.dp, top = 4.dp, bottom = 4.dp)
                    .background(APABatteryGray)
            )
        }
    }
}
