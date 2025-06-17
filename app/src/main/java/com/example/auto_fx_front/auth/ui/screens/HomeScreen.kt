// src/main/java/com/example/auto_fx_front/ui/screens/HomeScreen.kt
package com.example.auto_fx_front.auth.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio") },
                // Sin navigationIcon,
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Cerrar sesión")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("¡Bienvenido!", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
