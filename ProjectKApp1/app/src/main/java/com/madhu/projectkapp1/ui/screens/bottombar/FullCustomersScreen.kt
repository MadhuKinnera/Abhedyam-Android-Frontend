package com.madhu.projectkapp1.ui.screens.bottombar

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.components.MainAppBar
import com.madhu.projectkapp1.ui.components.MyFloatingBar
import com.madhu.projectkapp1.ui.components.MyTopAppBar
import com.madhu.projectkapp1.ui.navigation.BottomNavBar
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.screens.CustomersScreen
import com.madhu.projectkapp1.ui.viewmodel.CustomerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullCustomersScreen(
    navController: NavHostController,
    customerViewModel: CustomerViewModel = hiltViewModel()
) {


    val selectedItem = Screen.Customers

    val tag = "FullCustomersScreen"

    val text by customerViewModel.textState

    val searchWidgetState by customerViewModel.searchWidgetState

    LaunchedEffect(true) {
        customerViewModel.getCustomers()
    }

    val allCustomersResponse by customerViewModel.customerResponseModels.collectAsState()

    val searchedCustomersResponse by customerViewModel.searchedCustomers.collectAsState()

    var isSearched by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()



    Scaffold(
        topBar = {
            MyTopAppBar(navController = navController)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                MainAppBar(
                    defaultAppBarTitle = "Customers",
                    searchWidgetState = searchWidgetState,
                    searchTextState = text,
                    onTextChange = { customerViewModel.updateText(it) },
                    onCloseClicked = {
                        customerViewModel.updateText("")
                        customerViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                    },
                    onSearchClicked = {
                        Log.d(tag, "Search Triggered with $text")
                        customerViewModel.searchCustomersByName(text)
                        isSearched = true
                    },
                    onSearchTriggered = {
                        customerViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                    }
                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (isSearched) {
                        CustomersScreen(
                            navController = navController,
                            customersResponse = searchedCustomersResponse,
                            scrollState = scrollState
                        )

                    } else {
                        CustomersScreen(
                            navController = navController,
                            customersResponse = allCustomersResponse,
                            scrollState = scrollState
                        )
                    }
                    MyFloatingBar(
                        navController = navController,
                        scrollState = scrollState,
                        route = Screen.AddCustomer.route,
                        title = "Add Customer"
                    )
                }

            }

        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                selectedItem = selectedItem
            )
        }

    )

}


