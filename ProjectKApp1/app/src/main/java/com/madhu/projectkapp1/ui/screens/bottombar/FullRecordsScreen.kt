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
import com.madhu.projectkapp1.ui.screens.RecordScreen
import com.madhu.projectkapp1.ui.viewmodel.RecordViewModel
import com.madhu.projectkapp1.utility.CoreUtility


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullRecordsScreen(
    navController: NavHostController,
    recordViewModel: RecordViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val selectedItem = Screen.Records

    val tag = "FullRecordsScreen"

    val scrollState = rememberLazyListState()

    LaunchedEffect(true) {
        recordViewModel.getRecords()
    }

    val text by recordViewModel.textState
    val searchWidgetState by recordViewModel.searchWidgetState

    val allRecordsResponse by recordViewModel.records.collectAsState()

    val searchedRecordsResponse by recordViewModel.searchedRecords.collectAsState()

    var isSearched by remember { mutableStateOf(false) }



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
                        defaultAppBarTitle = "Records",
                        searchWidgetState = searchWidgetState,
                        searchTextState = text,
                        onTextChange = { recordViewModel.updateText(it) },
                        onCloseClicked = {
                            recordViewModel.updateText("")
                            recordViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d(tag, "Search Triggered with $text")
                            recordViewModel.searchRecordByName(
                                text.toIntOrNull() ?: 0, text, text
                            )
                            isSearched = true
                        },
                        onSearchTriggered = {
                            recordViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        if (isSearched) {
                            RecordScreen(
                                navController = navController,
                                recordsResponse = searchedRecordsResponse,
                                scrollState = scrollState
                            )
                        } else {
                            RecordScreen(
                                navController = navController,
                                recordsResponse = allRecordsResponse,
                                scrollState = scrollState
                            )
                        }
                        MyFloatingBar(
                            navController = navController,
                            scrollState = scrollState,
                            route = Screen.AddRecord.route,
                            title = "Add Record"
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


