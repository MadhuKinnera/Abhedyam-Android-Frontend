package com.madhu.projectkapp1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.ProductResponseModel
import com.madhu.projectkapp1.ui.components.BackTopBar
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.components.ProductResponseModelDetail
import com.madhu.projectkapp1.ui.viewmodel.ProductViewModel
import com.madhu.projectkapp1.utility.ResourceState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productViewModel: ProductViewModel = hiltViewModel(),
    productId: Int,
    navController: NavHostController
) {

    val TAG = "ProductDetailScreen"

    Log.d(TAG, "Inside Product Detail Screen")

    LaunchedEffect(true) {
        productViewModel.getProductById(productId)
    }


    val productResponse by productViewModel.product.collectAsState()

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }


    when (productResponse) {
        is ResourceState.Loading -> {
            Log.d(TAG, "Loading Product Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {


            Scaffold(
                topBar = {
                    BackTopBar(navController = navController, screenTitle = "Product Details")
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) {

                Column(modifier = Modifier.padding(it)) {
                    Log.d(TAG, "Product Data Fetched Success ")
                    val product =
                        (productResponse as ResourceState.Success<ProductResponseModel>).data
                    ProductResponseModelDetail(
                        productResponseModel = product,
                        navController = navController,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }

        is ResourceState.Error -> {
            Log.d(TAG, "Failed To Fetch Product Data")
            val error = (productResponse as ResourceState.Error<ProductResponseModel>).error
            Column {
                ErrorScreen(error)
            }


        }

    }


}