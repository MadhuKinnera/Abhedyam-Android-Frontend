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
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.ui.components.BackTopBar
import com.madhu.projectkapp1.ui.components.CustomerResponseModelDetail
//import com.madhu.projectkapp1.ui.components.DisplayResponse
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.viewmodel.CustomerViewModel
import com.madhu.projectkapp1.utility.ResourceState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    customerViewModel: CustomerViewModel = hiltViewModel(),
    customerId: Int,
    navController: NavHostController
) {
    val tag = "CustomerDetailScreen"

    Log.d(tag, "Inside Customer Detail Screen")

    LaunchedEffect(true) {
        customerViewModel.getCustomerById(customerId)
    }

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val customerResponse by customerViewModel.customerResponseModel.collectAsState()

    Scaffold(
        topBar = {
            BackTopBar(navController = navController, screenTitle = "Customer Details")
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {

        when (customerResponse) {
            is ResourceState.Loading -> {
                Log.d(tag, "Loading Customer Data")
                LoadingScreen()
            }

            is ResourceState.Success -> {
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    Log.d(tag, "Customer Data Fetched Success ")
                    val customer =
                        (customerResponse as ResourceState.Success<CustomerResponseModel>).data
                    CustomerResponseModelDetail(
                        customerResponseModel = customer,
                        navController = navController,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                }
            }

            is ResourceState.Error -> {
                Log.d(tag, "Failed To Fetch Customer Data")
                Column {
                    Text(text = "Some thing went wrong ${(customerResponse as ResourceState.Error<CustomerResponseModel>).error}")
                }
            }
        }
    }
}