package com.cocido.apa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(APAWhite)
    ) {
        // StatusBar
        StatusBar()
        
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 128.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo grande
            APALogo(
                size = LogoSize.LARGE,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Campo de correo electrónico
            APATextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Correo electrónico",
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Campo de contraseña
            APATextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Olvidé mi contraseña
            Text(
                text = "Olvidé mi contraseña",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = APABlue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onForgotPasswordClick)
            )
            
            // Checkbox Recordarme
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                CustomCheckbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Recordarme",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAGray
                )
            }
            
            // Botón Iniciar Sesión
            APAButton(
                text = "Iniciar Sesión",
                onClick = onLoginClick,
                isPrimary = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Botón Registrarse
            APAButton(
                text = "Registrarse",
                onClick = onRegisterClick,
                isPrimary = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Handle de navegación inferior
        NavigationHandle()
    }
}

@Composable
private fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(18.dp)
            .background(
                if (checked) APABlue else ComposeColor.Transparent,
                RoundedCornerShape(2.dp)
            )
            .then(
                if (!checked) {
                    Modifier.border(2.dp, APALightGray, RoundedCornerShape(2.dp))
                } else {
                    Modifier
                }
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Text(
                text = "✓",
                fontSize = 12.sp,
                color = APAWhite,
                fontWeight = FontWeight.Bold
            )
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
