package com.cocido.apa.ui.screens

// Diseño base: screen-2.png (APA_png)

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
import androidx.compose.ui.tooling.preview.Preview
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
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 112.dp,
                    bottom = 64.dp
                ),
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
            
            // Texto intermedio y botón Iniciar sesión (orden como en diseño)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "¿Ya tenés cuenta?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = APAGray
                )
                APAButton(
                    text = "Iniciar Sesión",
                    onClick = onLoginClick,
                    isPrimary = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
    }
}

@Preview(showBackground = true, name = "Register Screen")
@Composable
private fun RegisterScreenPreview() {
    APATheme {
        RegisterScreen(
            onRegisterClick = {},
            onLoginClick = {}
        )
    }
}

