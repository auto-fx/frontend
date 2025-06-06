package com.example.auto_fx_front.presentation.auth.signup

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
fun SignUpScreen(onNavigateToSignIn: () -> Unit) {
    val viewModel: SignUpViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(AuthProvider.realRepository) as T
        }
    })

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(SignUpEvent.ClearMessages)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Sign Up", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.firstName,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.FirstNameChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("First Name") }
            )
            OutlinedTextField(
                value = state.lastName,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.LastNameChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("Last Name") }
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EmailChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("Email") }
            )
            OutlinedTextField(
                value = state.countryCode,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.CountryCodeChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("Country Code") }
            )
            OutlinedTextField(
                value = state.number,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.NumberChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("Phone Number") }
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.PasswordChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = state.role,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.RoleChanged(it))
                    viewModel.onEvent(SignUpEvent.ClearMessages)
                },
                label = { Text("Role") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(SignUpEvent.Submit) },
                enabled = !state.isLoading
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onNavigateToSignIn) {
                Text("Already have an account? Log in")
            }

            if (state.success) {
                Text("Registro exitoso!", color = MaterialTheme.colorScheme.primary)
            }

            state.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
