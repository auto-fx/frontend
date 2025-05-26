package com.example.auto_fx_front.presentation.auth.signin

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
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToUpdatePassword: () -> Unit
) {
    val viewModel: SignInViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(AuthProvider.fakeRepository) as T
        }
    })

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(SignInEvent.ClearMessages)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Sign in to your Account", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(SignInEvent.EmailChanged(it))
                    viewModel.onEvent(SignInEvent.ClearMessages)
                },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(SignInEvent.PasswordChanged(it))
                    viewModel.onEvent(SignInEvent.ClearMessages)
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(SignInEvent.Submit) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log In")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onNavigateToUpdatePassword) {
                Text("Forgot your password?")
            }

            TextButton(onClick = onNavigateToSignUp) {
                Text("Don't have an account? Sign up")
            }

            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
