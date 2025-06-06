package com.example.auto_fx_front.presentation.auth.update_password

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.auto_fx_front.auth.data.AuthProvider

@Composable
fun UpdatePasswordScreen() {
    val viewModel: UpdatePasswordViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return UpdatePasswordViewModel(AuthProvider.realRepository) as T
        }
    })

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(UpdatePasswordEvent.ClearMessages)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Change Password", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = state.currentPassword,
                onValueChange = {
                    viewModel.onEvent(UpdatePasswordEvent.CurrentPasswordChanged(it))
                    viewModel.onEvent(UpdatePasswordEvent.ClearMessages)
                },
                label = { Text("Current Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.newPassword,
                onValueChange = {
                    viewModel.onEvent(UpdatePasswordEvent.NewPasswordChanged(it))
                    viewModel.onEvent(UpdatePasswordEvent.ClearMessages)
                },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(UpdatePasswordEvent.Submit) },
                enabled = !state.isLoading
            ) {
                Text("Update Password")
            }

            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            if (state.success) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Contraseña actualizada correctamente", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
