package com.madhu.projectkapp1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.GenericItem
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.utility.CoreUtility
import com.madhu.projectkapp1.utility.DefaultImageType
import com.madhu.projectkapp1.utility.ResourceState


@Composable
fun ProductScreen(
    navController: NavHostController,
    productsResponse: ResourceState<List<ProductResponseModel>>,
    scrollState: LazyListState
) {


    val tag = "ProductScreen"

    Log.d(tag, "Inside Product Screen")


    when (productsResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Product Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {

            Log.d(tag, "Products Data Fetched Success ")
            val products =
                productsResponse.data

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                state = scrollState
            ) {
                items(items = products, key = { it.product.productId!! }) {

                    GenericItem(
                        elements = listOf(
                            it.product.productName,
                            it.product.buyedPrice.toString(),
                            it.productSellCount.toString()
                        ),
                        imageUrl = it.product.imageUrl,
                        navController = navController,
                        borderWidth = 0.dp,
                        route = CoreUtility.trimRoute(Screen.ProductDetail.route) + it.product.productId,
                        colors = listOf(
                            Color.Black,
                            Color.Blue,
                            MaterialTheme.colorScheme.primary
                        ),
                        defaultImageType = DefaultImageType.PRODUCT

                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }

            }

        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Products Data")
            val error = productsResponse.error
            ErrorScreen(error)


        }

    }

}