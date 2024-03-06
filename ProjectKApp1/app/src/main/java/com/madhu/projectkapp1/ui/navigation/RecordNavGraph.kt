package com.madhu.projectkapp1.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.madhu.projectkapp1.ui.screens.AddRecordScreen
import com.madhu.projectkapp1.ui.screens.RecordDetailScreen
import com.madhu.projectkapp1.ui.screens.bottombar.FullRecordsScreen

fun NavGraphBuilder.RecordNavGraph(
    navController: NavHostController
){
    navigation(
        route = RECORD_ROUTE,
        startDestination = Screen.Records.route
    ) {
        composable(Screen.Records.route) {
            FullRecordsScreen(navController = navController)
        }
        composable(
            route = Screen.RecordDetail.route,
            arguments = listOf(navArgument(RECORD_ID) {
                type = NavType.IntType
            })

        ) {
            val recordId = it.arguments?.getInt(RECORD_ID) ?: 0
            RecordDetailScreen(recordId = recordId, navController = navController)
        }


        composable(Screen.AddRecord.route) {
            AddRecordScreen(navController = navController)
        }

    }

}
