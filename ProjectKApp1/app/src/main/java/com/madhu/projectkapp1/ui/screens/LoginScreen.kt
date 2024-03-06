package com.madhu.projectkapp1.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.ui.components.CommonButton
import com.madhu.projectkapp1.ui.components.ErrorScreen
import com.madhu.projectkapp1.ui.components.LoadingScreen
import com.madhu.projectkapp1.ui.components.NoInternetScreen
import com.madhu.projectkapp1.ui.components.TextFieldTypeOne
import com.madhu.projectkapp1.ui.navigation.AUTHENTICATION_ROUTE
import com.madhu.projectkapp1.ui.navigation.CUSTOMER_ROUTE
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.theme.OxygenMonoFontFamily
import com.madhu.projectkapp1.ui.viewmodel.LoginViewModel
import com.madhu.projectkapp1.utility.CoreUtility
import com.madhu.projectkapp1.utility.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val tag = "AppLoginScreen"

    val context = LocalContext.current

    if (!CoreUtility.isInternetConnected(context)) {
        Log.d(tag, "Internet is Not Connected")
        NoInternetScreen(
            navController = navController
        )
    } else {

        var isLoggedIn by remember { mutableStateOf(false) }
        var isCustomerLoggedIn by remember { mutableStateOf(false) }

        //loginViewModel.clearTokenFromStorage()

        LaunchedEffect(loginViewModel) {
            isLoggedIn = loginViewModel.isAlreadyLoggedIn()
            isCustomerLoggedIn = loginViewModel.isCustomerAlreadyLoggedIn()
        }

        if (isLoggedIn) {
            //  Text("Already Logged In")
            Log.d(tag, "Already Logged In")
            Log.d(tag, "Navigating to Customers Screen")
            navController.navigate(CUSTOMER_ROUTE)
        } else if (isCustomerLoggedIn) {
            navController.navigate(Screen.CustomerLogin.route)
        } else {
            getPermissions()

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Login",
                                color = Color.Black,
                                fontFamily = OxygenMonoFontFamily,
                                fontWeight = FontWeight.ExtraBold
                            )
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color(0xFFFDD835)
                        )
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Log.d(tag, "User Not Logged In ")
                    LoginScreen(navController = navController)

                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isFilled by remember { mutableStateOf(false) }

    isFilled =
        email.isNotEmpty() && password.isNotEmpty() && password.length >= 8 && email.contains("@gmail.com")


    val focusManager = LocalFocusManager.current


    val tag = "LoginScreen"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {


        TextFieldTypeOne(
            value = email,
            label = "Email",
            onValueChange = { email = it },
            focusManager = focusManager
        )

        TextFieldTypeOne(
            value = password,
            label = "Password",
            sideText = "min length 8",
            description =  "login password",
            onValueChange = { password = it },
            focusManager = focusManager,
            isPassword = true,
            isDone = true
        )

        Spacer(modifier = Modifier.height(28.dp))

        CommonButton(
            buttonText = "Login",
            isFilled = isFilled,
            onClick = {
                navController.navigate(CoreUtility.trimRoute(Screen.LoginResponse.route) + email + "/" + password)
            },
            description = "Effortlessly access your account with the login."
        )


        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = "New User?",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.CreateUser.route)
                }
            )

            Text(text = "OR", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Text(text = "Customer?",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.CustomerLogin.route)
                }
            )

        }


    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginResponse(
    email: String,
    password: String,
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val tag = "LoginResponse"

    LaunchedEffect(true) {
        loginViewModel.loginUser(email, password)
    }

    val loginResponse by loginViewModel.jwtResponse.collectAsState()


    Log.d(tag, "Inside Login Response")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val scope = rememberCoroutineScope()


        when (loginResponse) {
            is ResourceState.Loading -> {
                Log.d(tag, "Loading Login Data")
                LoadingScreen()
            }

            is ResourceState.Success -> {
                val jwtResponse = (loginResponse as ResourceState.Success<JwtResponse>).data
                val token = jwtResponse.jwtToken

                if (token.contains("ey")) {
                    loginViewModel.saveTokenInStorage("Bearer $token")
                    Text(text = "Login Success", color = Color.Green)
                    scope.launch {
                        delay(800)
                        navController.navigate(CUSTOMER_ROUTE)
                    }

                } else {
                    ErrorScreen(token)
                    scope.launch {
                        delay(1000)
                        navController.navigate(AUTHENTICATION_ROUTE)
                    }

                }


            }


            is ResourceState.Error -> {
                Log.d(tag, "Failed To Fetch Product Data")
                val error = (loginResponse as ResourceState.Error<JwtResponse>).error
                ErrorScreen(error)
                scope.launch {
                    delay(1000)
                    navController.navigate(AUTHENTICATION_ROUTE)
                }

            }

        }


    }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun getPermissions() {

    val permissionState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_MEDIA_IMAGES,
            )
        )

    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchMultiplePermissionRequest()
                }

                else -> {

                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }


    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        permissionState.permissions.forEach { it ->


            when (it.permission) {
                android.Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    if (it.status.isGranted) {
                        Text(text = "Storage Permission is Granted")
                    } else {
                        val text = if (it.status.shouldShowRationale) {
                            "Permission Required for Storage"
                        } else {
                            "Please Provide the Storage Permission"
                        }
                        Text(text = text)
                    }
                }

                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE -> {
                    if (it.status.isGranted) {
                        Text(text = "Manage Storage Permission is Granted")
                    } else {
                        val text = if (it.status.shouldShowRationale) {
                            "Permission Required for Manage Storage"
                        } else {
                            "Please Provide the Manage Storage Permission"
                        }
                        Text(text = text)
                    }


                }

                android.Manifest.permission.READ_MEDIA_IMAGES -> {
                    if (it.status.isGranted) {
                        Text(text = "Read Media Images Permission is Granted")
                    } else {
                        val text = if (it.status.shouldShowRationale) {
                            "Permission Required for Read Media Images"
                        } else {
                            "Please Provide the Read Media Images Permission"
                        }
                        Text(text = text)
                    }


                }


            }
        }

    }

}


