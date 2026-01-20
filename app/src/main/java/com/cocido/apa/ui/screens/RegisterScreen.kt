package com.cocido.apa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.components.*
import com.cocido.apa.ui.theme.*
import com.cocido.apa.ui.components.LogoSize

@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
                .padding(horizontal = 32.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo grande
            APALogo(
                size = LogoSize.LARGE,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Campo Nombre
            APATextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = "Nombre",
                modifier = Modifier.fillMaxWidth()
            )
            
            // Campo Apellido
            APATextField(
                value = apellido,
                onValueChange = { apellido = it },
                placeholder = "Apellido",
                modifier = Modifier.fillMaxWidth()
            )
            
            // Campo Correo electrónico
            APATextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Correo electrónico",
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Campo Contraseña
            APATextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Campo Repetir contraseña
            APATextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Repetir contraseña",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Botón Registrarse
            APAButton(
                text = "Registrarse",
                onClick = onRegisterClick,
                isPrimary = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Botón Ya tengo cuenta
            APAButton(
                text = "Ya tengo cuenta",
                onClick = onLoginClick,
                isPrimary = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Handle de navegación inferior
        NavigationHandle()
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
