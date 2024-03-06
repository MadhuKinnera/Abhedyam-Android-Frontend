package com.madhu.projectkapp1.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.madhu.projectkapp1.ui.screens.AddVillageScreen
import com.madhu.projectkapp1.ui.screens.VillageDetailScreen
import com.madhu.projectkapp1.ui.screens.bottombar.FullVillagesScreen


fun NavGraphBuilder.VillageNavGraph(
    navController: NavHostController
){


    navigation(
        route = VILLAGE_ROUTE,
        startDestination = Screen.Villages.route
    ) {
        composable(Screen.Villages.route) {
            FullVillagesScreen(navController = navController)
        }
        composable(
            route = Screen.VillageDetail.route,
            arguments = listOf(navArgument(VILLAGE_ID) {
                type = NavType.IntType
            })

        ) {
            val villageId = it.arguments?.getInt(VILLAGE_ID) ?: 0
            VillageDetailScreen(villageId = villageId, navController = navController)
        }




        composable(Screen.AddVillage.route) {
            AddVillageScreen(navController = navController)
        }
    }


}