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
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.GenericItem
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.utility.CoreUtility
import com.madhu.projectkapp1.utility.ResourceState

@Composable
fun RecordScreen(
    navController: NavHostController,
    recordsResponse:ResourceState<List<RecordResponseModel>>,
    scrollState: LazyListState
) {

    val tag = "RecordScreen"


    Log.d(tag, "Inside Record Screen")



    when (recordsResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Record Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {

                Log.d(tag, "Records Data Fetched Success ")


                val records =
                    recordsResponse.data



                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    state = scrollState

                    ) {
                    items(items = records, key = { it.saleRecord.recordId }) { it ->

                        val customerName = it.customer.customerName

                        val productName = it.saleRecord.product.productName
                        GenericItem(
                            elements = listOf(
                                customerName,
                                productName,
                                it.saleRecord.dueAmount.toString()
                            ),
                            navController = navController,
                            route = CoreUtility.trimRoute(Screen.RecordDetail.route) + (it.saleRecord.recordId),
                            borderWidth = 0.dp

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }



        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Records Data")
            val error = recordsResponse.error
            ErrorScreen(error)


        }

    }

}