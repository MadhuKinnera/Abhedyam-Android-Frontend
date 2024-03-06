package com.madhu.projectkapp1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.ui.components.BackTopBar
import com.madhu.projectkapp1.ui.components.CommonButton
import com.madhu.projectkapp1.ui.navigation.AUTHENTICATION_ROUTE
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.theme.kdamFontFamily
import com.madhu.projectkapp1.ui.viewmodel.LoginViewModel
import com.madhu.projectkapp1.utility.CoreUtility

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerLogin(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val tag = "CustomerLogin"

    var customerCode by remember { mutableStateOf("") }

    var isFilled by remember { mutableStateOf(false) }

    isFilled = customerCode.isNotEmpty() && customerCode.length >= 6

    var isCustomerLoggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        isCustomerLoggedIn = loginViewModel.isCustomerAlreadyLoggedIn()
        Log.d(tag, "Customer Login is $isCustomerLoggedIn")
        loginViewModel.getLoggedInCustomerCode()
        if (loginViewModel.customerCode.value != "")
            customerCode = loginViewModel.customerCode.value
        Log.d(tag, "LoggedIn Customer Code is $customerCode")
        if (isCustomerLoggedIn && customerCode.isNotEmpty()) {
            navController.navigate(CoreUtility.trimRoute(Screen.CustomerInfo.route) + customerCode)
        }
    }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            BackTopBar(navController = navController, route = AUTHENTICATION_ROUTE)
        }
    ) { padding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = customerCode,
                onValueChange = { customerCode = it },
                label = {
                    Text(text = "Customer Code")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(true)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp))
                    .semantics {
                        contentDescription = "Customer Code"
                    },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    fontFamily = kdamFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0xFF0E312D)
                ),
                singleLine = true,

                )

            Spacer(modifier = Modifier.height(32.dp))

            CommonButton(
                buttonText = "Login",
                isFilled = isFilled,
                onClick = {
                    navController.navigate(CoreUtility.trimRoute(Screen.CustomerInfo.route) + customerCode)
                },
                description = "Customer Login"
            )

        }

    }
}