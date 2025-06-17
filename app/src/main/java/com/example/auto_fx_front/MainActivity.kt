// src/main/java/com/example/auto_fx_front/MainActivity.kt
package com.example.auto_fx_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.auto_fx_front.auth.presentation.viewmodel.AuthViewModel
import com.example.auto_fx_front.auth.presentation.viewmodel.SessionState
import com.example.auto_fx_front.auth.presentation.viewmodel.SplashViewModel
import com.example.auto_fx_front.auth.ui.screens.FullScreenLoading
import com.example.auto_fx_front.auth.ui.screens.HomeScreen
import com.example.auto_fx_front.auth.ui.screens.LoginScreen
import com.example.auto_fx_front.auth.ui.screens.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inicializamos el DataStore
        Injection.init(this)

        setContent {
            val nav = rememberNavController()

            NavHost(nav, startDestination = "splash") {
                composable("splash") {
                    // obtenemos SplashViewModel vía factory
                    val factory = Injection.provideSplashViewModelFactory()
                    val vm = ViewModelProvider(this@MainActivity, factory)
                        .get(SplashViewModel::class.java)

                    when (val st = vm.uiState.collectAsState().value) {
                        SessionState.Checking      -> FullScreenLoading()
                        SessionState.Authenticated -> LaunchedEffect(Unit) {
                            nav.navigate("home"){ popUpTo("splash"){ inclusive=true } }
                        }
                        SessionState.Unauthenticated -> LaunchedEffect(Unit) {
                            nav.navigate("login"){ popUpTo("splash"){ inclusive=true } }
                        }
                    }
                }

                composable("login") {
                    val factory = Injection.provideAuthViewModelFactory()
                    val vm = ViewModelProvider(this@MainActivity, factory)
                        .get(AuthViewModel::class.java)

                    LoginScreen(
                        vm,
                        onLoginSuccess = {
                            nav.navigate("home") { popUpTo("login") { inclusive = true } }
                        },
                        onSignUp = { nav.navigate("signup") }
                    )
                }

                composable("signup") {
                    val factory = Injection.provideAuthViewModelFactory()
                    val vm = ViewModelProvider(this@MainActivity, factory)
                        .get(AuthViewModel::class.java)

                    SignUpScreen(vm) {
                        nav.popBackStack()
                    }
                }

                composable("home") {
                    HomeScreen {
                        nav.navigate("login") { popUpTo("home") { inclusive = true } }
                    }
                }
            }
        }
    }
}
