package com.madhu.projectkapp1.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.madhu.projectkapp1.ui.screens.AddProductScreen
import com.madhu.projectkapp1.ui.screens.ProductDetailScreen
import com.madhu.projectkapp1.ui.screens.bottombar.FullProductsScreen


fun NavGraphBuilder.ProductNavGraph(
    navController : NavHostController
){


    navigation(
        route = PRODUCT_ROUTE,
        startDestination = Screen.Products.route
    ) {

        composable(Screen.Products.route) {
            FullProductsScreen(navController = navController)
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument(PRODUCT_ID) {
                type = NavType.IntType
            })

        ) {
            val productId = it.arguments?.getInt(PRODUCT_ID) ?: 0
            ProductDetailScreen(productId = productId, navController = navController)
        }

        composable(Screen.AddProduct.route) {
            AddProductScreen(navController = navController)
        }




    }


}