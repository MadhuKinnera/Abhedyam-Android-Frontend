package com.madhu.projectkapp1.ui.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.madhu.projectkapp1.ui.components.DisplayCustomersNames
import com.madhu.projectkapp1.ui.screens.AddCustomerScreen
import com.madhu.projectkapp1.ui.screens.CustomerDetailScreen
import com.madhu.projectkapp1.ui.screens.CustomerLogin
import com.madhu.projectkapp1.ui.screens.CustomerPersonalInfo
import com.madhu.projectkapp1.ui.screens.bottombar.FullCustomersScreen


fun NavGraphBuilder.CustomerNagGraph(
    navController: NavHostController
) {

    val tag = "CustomerNagGraph"

    navigation(
        route = CUSTOMER_ROUTE,
        startDestination = Screen.Customers.route
    ) {
        composable(Screen.Customers.route) {
            FullCustomersScreen(navController = navController)
        }
        composable(
            route = Screen.CustomerDetail.route,
            arguments = listOf(navArgument(CUSTOMER_ID) {

                type = NavType.IntType
            })

        ) {
            val customerId = it.arguments?.getInt(CUSTOMER_ID) ?: 0
            Log.d(tag, "Customer Id is $customerId")
            CustomerDetailScreen(customerId = customerId, navController = navController)
        }


        composable(Screen.AddCustomer.route) {
            AddCustomerScreen(navController = navController)
        }

        composable(Screen.DisplayCustomersNames.route) {
            DisplayCustomersNames(navController = navController)
        }

        composable(Screen.CustomerLogin.route) {
            CustomerLogin(navController = navController)
        }

        composable(route = Screen.CustomerInfo.route,
            arguments = listOf(
                navArgument(CUSTOMER_CODE) {
                    type = NavType.StringType
                }
            )
        ) {

            val customerCode = it.arguments?.getString(CUSTOMER_CODE).orEmpty()

            CustomerPersonalInfo(navController = navController, customerCode = customerCode)

        }


    }

}