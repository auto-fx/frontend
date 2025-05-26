package com.example.auto_fx_front.presentation.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.auto_fx_front.presentation.auth.signin.SignInScreen
import com.example.auto_fx_front.presentation.auth.signup.SignUpScreen
import com.example.auto_fx_front.presentation.auth.update_password.UpdatePasswordScreen

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthScreens.SignIn.route
    ) {
        composable(AuthScreens.SignIn.route) {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(AuthScreens.SignUp.route) },
                onNavigateToUpdatePassword = { navController.navigate(AuthScreens.UpdatePassword.route) }
            )
        }
        composable(AuthScreens.SignUp.route) {
            SignUpScreen(onNavigateToSignIn = {
                navController.popBackStack()
            })
        }
        composable(AuthScreens.UpdatePassword.route) {
            UpdatePasswordScreen()
        }
    }
}
