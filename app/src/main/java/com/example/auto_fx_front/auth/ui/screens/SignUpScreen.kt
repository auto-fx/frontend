// src/main/java/com/example/auto_fx_front/ui/screens/SignUpScreen.kt
package com.example.auto_fx_front.auth.ui.screens

import androidx.compose.foundation.layout.*
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
fun SignUpScreen(
    vm: AuthViewModel,
    onSignUpSuccess: () -> Unit
) {
    var showPwd by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = vm.firstName,
            onValueChange = { vm.firstName = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = vm.lastName,
            onValueChange = { vm.lastName = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = vm.emailAddr,
            onValueChange = { vm.emailAddr = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = vm.countryCode,
                onValueChange = { vm.countryCode = it },
                label = { Text("Código país") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = vm.phoneNumber,
                onValueChange = { vm.phoneNumber = it },
                label = { Text("Número") },
                modifier = Modifier.weight(2f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(Modifier.height(8.dp))
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
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(vm.keepSignedIn, onCheckedChange = { vm.keepSignedIn = it })
            Text("Mantener sesión iniciada")
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { vm.signUp(onSignUpSuccess) },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (vm.uiState is AuthUIState.Loading)
                CircularProgressIndicator(Modifier.size(24.dp), strokeWidth = 2.dp)
            else
                Text("Registrar")
        }
        if (vm.uiState is AuthUIState.Error) {
            Text(
                text = (vm.uiState as AuthUIState.Error).msg,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
