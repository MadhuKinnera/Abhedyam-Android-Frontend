package com.madhu.projectkapp1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.RecordResponseModel
import com.madhu.projectkapp1.ui.components.AddTransaction
import com.madhu.projectkapp1.ui.components.BackTopBar
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.components.RecordResponseModelDetail
import com.madhu.projectkapp1.ui.viewmodel.RecordViewModel
import com.madhu.projectkapp1.ui.viewmodel.TransactionViewModel
import com.madhu.projectkapp1.utility.ResourceState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailScreen(
    recordViewModel: RecordViewModel = hiltViewModel(),
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    recordId: Int,
    navController: NavHostController
) {

    val tag = "RecordDetailScreen"

    LaunchedEffect(true) {
        recordViewModel.getRecordById(recordId)
    }

    Log.d(tag, "Inside Record Detail Screen")

    val recordResponse by recordViewModel.record.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var refreshTime by remember { mutableIntStateOf(0) }

    val addedTransaction by transactionViewModel.addedTransaction.collectAsState()

    LaunchedEffect(addedTransaction) {
        refreshTime = +1
        Log.d(tag, "recomposing $refreshTime th time")
        recordViewModel.getRecordById(recordId)
    }

    when (recordResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading Record Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {

            Scaffold(
                topBar = {
                    BackTopBar(navController = navController, screenTitle = "Record Details")
                },
                floatingActionButton = {
                    FloatingActionButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            showDialog = true
                        },
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Transaction"
                        )

                    }
                }

            ) {
                Column(modifier = Modifier.padding(it)) {
                    Log.d(tag, "Record Data Fetched Success ")
                    val recordResponseModel =
                        (recordResponse as ResourceState.Success<RecordResponseModel>).data
                    RecordResponseModelDetail(recordResponseModel)

                    if (recordResponseModel.saleRecord.dueAmount != 0) {
                        AddTransaction(
                            showAlert = showDialog,
                            onShowAlertClick = { showDialog = false },
                            recordId = recordResponseModel.saleRecord.recordId,
                        )
                    }
                }


            }
        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch Record Data")

            val error = (recordResponse as ResourceState.Error<RecordResponseModel>).error
            ErrorScreen(error)

        }

    }


}