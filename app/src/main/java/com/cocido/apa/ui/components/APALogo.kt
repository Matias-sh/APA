package com.cocido.apa.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cocido.apa.R

enum class LogoSize {
    SMALL, // Para headers: 40dp x 92dp
    LARGE  // Para login/register: 148.5dp x 238.25dp
}

@Composable
fun APALogo(
    modifier: Modifier = Modifier,
    size: LogoSize = LogoSize.SMALL
) {
    when (size) {
        LogoSize.SMALL -> {
            // Logo para headers (un poco más grande y legible)
            Box(
                modifier = modifier
                    .width(92.dp)
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apa_logo),
                    contentDescription = "Logo APA Supermercados",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
        LogoSize.LARGE -> {
            // Logo grande para login/register (148.5dp altura, 238.25dp ancho)
            Box(
                modifier = modifier
                    .width(238.25.dp)
                    .height(148.5.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apa_logo),
                    contentDescription = "Logo APA Supermercados",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
