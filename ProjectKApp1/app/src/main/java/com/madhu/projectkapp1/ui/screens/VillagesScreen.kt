package com.madhu.projectkapp1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.GenericItem
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.utility.CoreUtility
import com.madhu.projectkapp1.utility.DefaultImageType
import com.madhu.projectkapp1.utility.ResourceState


@Composable
fun VillageScreen(
    navController: NavHostController,
    villageResponse: ResourceState<List<VillageWiseResponse>>,
    scrollState:LazyListState
) {

    val tag = "VillageScreen"


    Log.d(tag, "Inside Village Screen")



    when (villageResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Village Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {

                Log.d(tag, "Village Data Fetched Success ")
                val villagesData =
                    villageResponse.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp),
                    state = scrollState
                ) {
                    items(items = villagesData, key = { it.village.villageId!! }) {
                        GenericItem(
                            elements = listOf(
                                it.village.villageName,
                                it.totalRecordsCount.toString(),
                                it.totalAmountFromVillage.toString()
                            ),
                            imageUrl = it.village.imageUrl,
                            navController = navController,
                            route = CoreUtility.trimRoute(Screen.VillageDetail.route) + (it.village.villageId),
                            defaultImageType = DefaultImageType.VILLAGE,
                            borderWidth = 0.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }


        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Records Data")
            val error = villageResponse.error
            ErrorScreen(error)

        }

    }
}