// src/main/java/com/example/auto_fx_front/ui/screens/LoginScreen.kt
package com.example.auto_fx_front.auth.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.auto_fx_front.auth.presentation.viewmodel.AuthUIState
import com.example.auto_fx_front.auth.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    vm: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onSignUp: () -> Unit
) {
    var showPwd by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,    // ← empezamos desde arriba
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email
        TextField(
            value = vm.emailAddr,
            onValueChange = { vm.emailAddr = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(Modifier.height(8.dp))

        // Contraseña
        TextField(
            value = vm.password,
            onValueChange = { vm.password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
                if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { showPwd = !showPwd }) {
                    Text(if (showPwd) "Ocultar" else "Mostrar")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(Modifier.height(16.dp))

        // Checkbox “Mantener sesión”
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(vm.keepSignedIn, onCheckedChange = { vm.keepSignedIn = it })
            Text("Mantener sesión iniciada")
        }
        Spacer(Modifier.height(24.dp))

        // Botón Entrar
        Button(
            onClick = { vm.login { onLoginSuccess() } },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (vm.uiState is AuthUIState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Entrar")
            }
        }
        Spacer(Modifier.height(12.dp))

        // Texto “Crear cuenta”
        TextButton(onClick = onSignUp) {
            Text("¿No tienes cuenta? Regístrate")
        }

        // Mensaje de error
        if (vm.uiState is AuthUIState.Error) {
            Text(
                text = (vm.uiState as AuthUIState.Error).msg,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
