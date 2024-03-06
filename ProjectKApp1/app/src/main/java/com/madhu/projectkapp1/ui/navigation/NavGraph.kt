package com.madhu.projectkapp1.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = AUTHENTICATION_ROUTE,
    ) {

        AuthNagGraph(navController = navController)

        CustomerNagGraph(navController = navController)

        ProductNavGraph(navController = navController)

        RecordNavGraph(navController = navController)

        VillageNavGraph(navController = navController)


    }


}