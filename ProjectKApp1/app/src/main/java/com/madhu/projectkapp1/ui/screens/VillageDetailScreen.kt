package com.madhu.projectkapp1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import com.madhu.projectkapp1.ui.components.BackTopBar
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.components.VillageResponseModelDetail
import com.madhu.projectkapp1.ui.viewmodel.VillageViewModel
import com.madhu.projectkapp1.utility.ResourceState

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun VillageDetailScreen(
    villageViewModel: VillageViewModel = hiltViewModel(),
    villageId: Int,
    navController: NavHostController
) {

    val tag = "VillageDetailScreen"

    LaunchedEffect(true) {
        villageViewModel.getVillageDataById(villageId)
    }

    Log.d(tag, "Inside Village Detail Screen")

    val villageResponse by villageViewModel.villageData.collectAsState()


    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }


    when (villageResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Record Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {

            Scaffold(
                topBar = {
                    BackTopBar(navController = navController, screenTitle = "Village Details")
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) {

                Column(modifier = Modifier.padding(it)) {
                    Log.d(tag, "Village Data Fetched Success ")
                    val villageResponseModel =
                        (villageResponse as ResourceState.Success<VillageWiseResponse>).data
                    VillageResponseModelDetail(
                        villageResponseModel = villageResponseModel,
                        navController = navController,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Record Data")
            Column {
                Text(text = "Some thing went wrong ")
            }


        }

    }


}