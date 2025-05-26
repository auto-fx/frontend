package com.example.auto_fx_front.presentation.auth.navigation

sealed class AuthScreens(val route: String) {
    object SignIn : AuthScreens("sign_in")
    object SignUp : AuthScreens("sign_up")
    object UpdatePassword : AuthScreens("update_password")

}
