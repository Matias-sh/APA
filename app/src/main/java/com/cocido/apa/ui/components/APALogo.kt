package com.cocido.apa.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.theme.APABlue

enum class LogoSize {
    SMALL, // Para headers: 31.2dp x 71.4dp
    LARGE  // Para login/register: 148.5dp x 238.25dp
}

@Composable
fun APALogo(
    modifier: Modifier = Modifier,
    size: LogoSize = LogoSize.SMALL
) {
    when (size) {
        LogoSize.SMALL -> {
            // Logo pequeño para headers (31.2dp altura, 71.4dp ancho)
            Box(
                modifier = modifier
                    .width(71.4.dp)
                    .height(31.2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "APA",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = APABlue
                )
            }
        }
        LogoSize.LARGE -> {
            // Logo grande para login/register (148.5dp altura, 238.25dp ancho)
            Column(
                modifier = modifier
                    .width(238.25.dp)
                    .height(148.5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "APA",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = APABlue,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "SUPERMERCADOS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = APABlue
                )
            }
        }
    }
}
