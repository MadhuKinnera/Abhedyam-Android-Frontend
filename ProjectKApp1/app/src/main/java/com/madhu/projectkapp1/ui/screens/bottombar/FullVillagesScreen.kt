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
import com.madhu.projectkapp1.ui.screens.VillageScreen
import com.madhu.projectkapp1.ui.viewmodel.VillageViewModel
import com.madhu.projectkapp1.utility.CoreUtility


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullVillagesScreen(
    navController: NavHostController,
    villageViewModel: VillageViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val selectedItem = Screen.Villages

    val tag = "FullVillagesScreen"


    LaunchedEffect(true) {
        villageViewModel.getVillagesData()
    }

    val text by villageViewModel.textState
    val searchWidgetState by villageViewModel.searchWidgetState

    val allVillagessResponse by villageViewModel.villagesData.collectAsState()

    val searchedVillagesResponse by villageViewModel.searchedVillages.collectAsState()

    var isSearched by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()





    if (!CoreUtility.isInternetConnected(context)) {
        Log.d(tag, "Internet is Not Connected")
        NoInternetScreen(
            navController = navController
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
                        defaultAppBarTitle = "Villages",
                        searchWidgetState = searchWidgetState,
                        searchTextState = text,
                        onTextChange = { villageViewModel.updateText(it) },
                        onCloseClicked = {
                            villageViewModel.updateText("")
                            villageViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d(tag, "Search Triggered with $text")
                            villageViewModel.searchVillagesByName(text)
                            isSearched = true
                        },
                        onSearchTriggered = {
                            villageViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        if (isSearched) {
                            VillageScreen(
                                navController = navController,
                                villageResponse = searchedVillagesResponse,
                                scrollState = scrollState
                            )
                        } else {
                            VillageScreen(
                                navController = navController,
                                villageResponse = allVillagessResponse,
                                scrollState = scrollState
                            )
                        }
                        MyFloatingBar(
                            navController = navController,
                            scrollState = scrollState,
                            route = Screen.AddVillage.route,
                            title = "Add Village"
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


