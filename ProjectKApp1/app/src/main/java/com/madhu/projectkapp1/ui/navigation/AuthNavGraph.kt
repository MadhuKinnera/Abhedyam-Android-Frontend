package com.madhu.projectkapp1.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.madhu.projectkapp1.ui.screens.AppLoginScreen
import com.madhu.projectkapp1.ui.screens.CreateUser
import com.madhu.projectkapp1.ui.screens.LoginResponse

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.AuthNagGraph(
    navController: NavHostController
) {

    val tag = "AuthNavGraph"

    navigation(
        route = AUTHENTICATION_ROUTE,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            AppLoginScreen(navController = navController)
        }

        composable(
            route = Screen.LoginResponse.route,
            arguments = listOf(navArgument(EMAIL) {
                type = NavType.StringType
            }, navArgument(PASSWORD) {
                type = NavType.StringType
            })

        ) {
            val email = it.arguments?.getString(EMAIL).orEmpty()
            val password = it.arguments?.getString(PASSWORD).orEmpty()

            Log.d(tag, "login details are $email $password")
            LoginResponse(navController = navController, email = email, password = password)
        }

        composable(Screen.CreateUser.route) {
            CreateUser(navController = navController)
        }

    }

}