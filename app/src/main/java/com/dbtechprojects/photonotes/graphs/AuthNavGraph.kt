package com.project.bicos_project.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dbtechprojects.photonotes.screens.logIn.ForgotContent
import com.dbtechprojects.photonotes.screens.logIn.LoginContent
import com.dbtechprojects.photonotes.screens.logIn.SignUpContent
//import com.dbtechprojects.photonotes.screens.logIn.login_mail

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginContent(
                loginClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpContent(name = AuthScreen.SignUp.route,navController) {
            }
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotContent(name = AuthScreen.Forgot.route) {}
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "1")
    object SignUp : AuthScreen(route = "2")
    object Forgot : AuthScreen(route = "3")
}
