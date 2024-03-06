package com.madhu.projectkapp1.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.CustomerResponseModel
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.GenericItem
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.navigation.AUTHENTICATION_ROUTE
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.viewmodel.LoginViewModel
import com.madhu.projectkapp1.utility.CoreUtility
import com.madhu.projectkapp1.utility.DefaultImageType
import com.madhu.projectkapp1.utility.ResourceState


@SuppressLint("UnrememberedMutableState")
@Composable
fun CustomersScreen(
    navController: NavHostController,
    customersResponse: ResourceState<List<CustomerResponseModel>>,
    loginViewModel: LoginViewModel = hiltViewModel(),
    scrollState: LazyListState
) {

    val tag = "CustomersScreen"

    Log.d(tag, "Inside Customers Screen")

    when (customersResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Customers Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {
            Log.d(tag, "Customers Data Fetched Success ")
            val customers =
                customersResponse.data
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                state = scrollState
            ) {
                items(items = customers, key = { it.customer.customerId }) {

                    GenericItem(
                        elements = listOf(
                            it.customer.customerName,
                            it.totalPaidAmount.toString(),
                            it.totalRemaininAmount.toString()
                        ),
                        imageUrl = it.customer.profileImageUrl,
                        navController = navController,
                        borderWidth = 0.dp,
                        defaultImageType = DefaultImageType.CUSTOMER,
                        route = CoreUtility.trimRoute(Screen.CustomerDetail.route) + it.customer.customerId
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }


        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Customers Data")
            Column {
                val error =
                    customersResponse.error
                ErrorScreen(message = error)
                if (error.contains("Login") || error.contains("UnAuthorized")) {
                    loginViewModel.clearTokenFromStorage()
                    navController.navigate(route = AUTHENTICATION_ROUTE) {
                        popUpTo(AUTHENTICATION_ROUTE) {
                            inclusive = false
                        }
                    }

                }

            }


        }

    }
}
