package com.cocido.apa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    // Barra superior vacía solo para respetar el espacio visual del diseño
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(Color.White)
    )
}
