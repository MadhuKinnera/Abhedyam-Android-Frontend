package com.madhu.projectkapp1.ui.screens.bottombar

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.components.MainAppBar
import com.madhu.projectkapp1.ui.components.MyFloatingBar
import com.madhu.projectkapp1.ui.components.MyTopAppBar
import com.madhu.projectkapp1.ui.components.NoInternetScreen
import com.madhu.projectkapp1.ui.navigation.BottomNavBar
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.screens.ProductScreen
import com.madhu.projectkapp1.ui.viewmodel.ProductViewModel
import com.madhu.projectkapp1.utility.CoreUtility


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullProductsScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val selectedItem = Screen.Products

    val tag = "FullProductsScreen"


    LaunchedEffect(true) {
        productViewModel.getProducts()
    }

    val text by productViewModel.textState
    val searchWidgetState by productViewModel.searchWidgetState

    val allProductsResponse by productViewModel.products.collectAsState()

    val searchedProductsResponse by productViewModel.searchedProducts.collectAsState()

    var isSearched by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()

    if (!CoreUtility.isInternetConnected(context)) {
        Log.d(tag, "Internet is Not Connected")
        NoInternetScreen(
            navController =navController
        )
    } else {

        Scaffold(
            topBar = {
                MyTopAppBar(navController = navController)
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {

                    MainAppBar(
                        defaultAppBarTitle = "Products",
                        searchWidgetState = searchWidgetState,
                        searchTextState = text,
                        onTextChange = { productViewModel.updateText(it) },
                        onCloseClicked = {
                            productViewModel.updateText("")
                            productViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d(tag, "Search Triggered with $text")
                            productViewModel.searchProductsByName(text)
                            isSearched = true
                        },
                        onSearchTriggered = {
                            productViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                        }
                    )


                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        if (isSearched) {
                            ProductScreen(
                                navController = navController,
                                productsResponse = searchedProductsResponse,
                                scrollState = scrollState
                            )
                        } else {
                            ProductScreen(
                                navController = navController,
                                productsResponse = allProductsResponse,
                                scrollState = scrollState
                            )
                        }
                        MyFloatingBar(
                            navController = navController,
                            scrollState = scrollState,
                            route = Screen.AddProduct.route,
                            title = "Add Product"
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
}


