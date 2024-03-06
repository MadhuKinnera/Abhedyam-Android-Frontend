package com.madhu.projectkapp1.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.ui.components.AddVillage
import com.madhu.projectkapp1.ui.components.BackTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVillageScreen(
    navController: NavHostController
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { BackTopBar(navController = navController, screenTitle = "Add Village") },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        AddVillage(
            navController = navController,
            modifier = Modifier.padding(it),
            snackbarHostState = snackbarHostState,
            scope = scope
        )
    }


}