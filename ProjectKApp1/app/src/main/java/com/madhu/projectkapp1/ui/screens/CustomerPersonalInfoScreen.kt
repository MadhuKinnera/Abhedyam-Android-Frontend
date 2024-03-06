package com.madhu.projectkapp1.ui.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.madhu.projectkapp1.R
import com.madhu.projectkapp1.data.entity.CustomerPersonalDto
import com.madhu.projectkapp1.ui.components.CoilImage
import com.madhu.projectkapp1.ui.components.DisplayCustomer
import com.madhu.projectkapp1.ui.components.DisplayMapElements
import com.madhu.projectkapp1.ui.components.DisplayRecord
import com.madhu.projectkapp1.ui.components.DisplayTransaction
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.navigation.AUTHENTICATION_ROUTE
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.theme.FiraMonoFontFamily
import com.madhu.projectkapp1.ui.viewmodel.CustomerViewModel
import com.madhu.projectkapp1.ui.viewmodel.LoginViewModel
import com.madhu.projectkapp1.utility.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomerPersonalInfo(
    navController: NavHostController,
    customerCode: String,
    customerViewModel: CustomerViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {


    val tag = "CustomerPersonalInfo"

    Log.d(tag, "Inside CustomerPersonalInfo Screen")

    val scope = rememberCoroutineScope()


    LaunchedEffect(true) {
        Log.d(tag, "The customerCode is $customerCode")
        customerViewModel.loginCustomer(customerCode)
    }

    val customersResponse by customerViewModel.loggedInCustomer.collectAsState()


    when (customersResponse) {
        is ResourceState.Loading -> {
            Log.d(tag, "Loading CustomerPersonalInfo Data")
            LoadingScreen()
        }

        is ResourceState.Success -> {
            Log.d(tag, "CustomerPersonalInfo Data Fetched Success ")
            val customerPersonalDto =
                (customersResponse as ResourceState.Success<CustomerPersonalDto>).data


            DisplayCustomerData(
                customerPersonalDto = customerPersonalDto,
                navController = navController,
                loginViewModel = loginViewModel
            )

        }

        is ResourceState.Error -> {
            Log.d(tag, "Failed To Fetch CustomerPersonalInfo Data")
            Column {
                val error =
                    (customersResponse as ResourceState.Error<CustomerPersonalDto>).error
                ErrorScreen(message = error)
                scope.launch {
                    delay(1000)
                    navController.navigate(Screen.CustomerLogin.route)
                }

            }


        }

    }


}


@Composable
fun DisplayCustomerData(
    customerPersonalDto: CustomerPersonalDto,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {

    val tag = "DisplayCustomerData"

    LaunchedEffect(true) {
        Log.d(tag, "Saving The Customer Code ")
        val alreadyExist = loginViewModel.isCustomerAlreadyLoggedIn()
        if (!alreadyExist)
            customerPersonalDto.customer.customerCode?.let {
                loginViewModel.saveCustomerCodeInStorage(
                    it
                )
            }
    }

    val customer = customerPersonalDto.customer

    val creditorName = customerPersonalDto.creditorName
    val creditorPhoneNumber = customerPersonalDto.creditorPhoneNumber
    val creditorQRImageUrl = customerPersonalDto.creditorQRImageUrl
    val creditorProfileImage = customerPersonalDto.creditorProfileImageUrl

    val records = customerPersonalDto.saleRecords
    val totalRecords = customerPersonalDto.totalRecords
    val totalAmount = customerPersonalDto.totalAmount
    val totalPaidAmount = customerPersonalDto.totalPaidAmount
    val totalRemainingAmount = customerPersonalDto.totalRemaininAmount
    val recordStatus = customerPersonalDto.recordStatus

    val productNames = customerPersonalDto.productNames
    val totalProducts = customerPersonalDto.totalProducts

    val totalTransactions = customerPersonalDto.totalTransactions


    val userMap = mutableMapOf<String, String>()

    userMap["Creditor Name"] = creditorName
    userMap["creditorPhoneNumber"] = creditorPhoneNumber.replace('-', '/')


    val recordMap = mutableMapOf<String, String>()

    recordMap["Total Records"] = totalRecords.toString()
    recordMap["Total Amount"] = totalAmount.toString()
    recordMap["Total Paid Amount"] = totalPaidAmount.toString()
    recordMap["Total Remaining Amount"] = totalRemainingAmount.toString()
    recordMap["Record Status"] = if(recordStatus) "Active" else "InActive"


    val productMap = mutableMapOf<String, String>()

    for (i in 0..productNames.size - 1) {
        productMap["Product ${i + 1}"] = productNames[i]
    }

    recordMap["Total Products"] = totalProducts.toString()
    recordMap["Total Transactions"] = totalTransactions.toString()





    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            CoilImage(
                imageUrl = customer.profileImageUrl,
                defaultPainter = painterResource(id = R.drawable.default_customer)
            )
        }
        item {
            DisplayCustomer(customer = customer, showTitle = true)
        }

        item {
            DisplayMapElements(
                title = "Creditor Details",
                map = userMap
            )
        }


        item {
            ShowQRCode(qrCodeUrl = creditorQRImageUrl)
        }

        item {
            ShowQRCode(qrCodeUrl = creditorProfileImage)
        }


        item {
            DisplayMapElements(
                title = "Overall Record Details",
                map = recordMap
            )
        }


        items(records) { record ->
            DisplayRecord(record = record, recordStatus = record.dueAmount > 0)
        }

        item {
            DisplayMapElements(title = "Product Details", map = productMap)
        }

        item {
            Column {
                LogOutButton(navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
            }

        }


    }


}


@Composable
fun LogOutButton(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val tag = "LogOut"

    ElevatedButton(
        onClick = {
            Log.d(tag, "Logging Out")
            loginViewModel.clearCustomerCodeFromStorage()
            Log.d(tag, "Clearing Customer Code from storage")
            navController.navigate(route = AUTHENTICATION_ROUTE) {
                popUpTo(AUTHENTICATION_ROUTE) {
                    inclusive = false
                }
            }
        },
        shape = RoundedCornerShape(
            bottomStart = 16.dp,
            topEnd = 16.dp
        ),
        modifier = Modifier
            .width(140.dp)
            .height(48.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD800FF)
        )
    ) {
        Text(text = "LogOut", fontSize = 20.sp, fontFamily = FiraMonoFontFamily)
    }


}


@Composable
fun ShowQRCode(qrCodeUrl: String?) {

    val tag = "ShowQRCode"

    Log.d(tag, "Inside ShowQRCode")

    if (qrCodeUrl != null) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {


            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(
                    LocalContext.current
                )
                    .data(qrCodeUrl)
                    .crossfade(true)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "QR Code Image",
                modifier = Modifier.size(320.dp)
            )

            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator()
            }
            if (painter.state is AsyncImagePainter.State.Success) {
                Log.d(tag, "Image Loaded")
            }

            if (painter.state is AsyncImagePainter.State.Error) {
                Log.d(tag, "Error While Loading ")
                ErrorScreen("Error While Loading The Image")
            }
        }

    }

}