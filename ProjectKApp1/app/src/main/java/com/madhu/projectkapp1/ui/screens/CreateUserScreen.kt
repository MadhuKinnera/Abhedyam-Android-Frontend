package com.madhu.projectkapp1.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.madhu.projectkapp1.data.entity.JwtResponse
import com.madhu.projectkapp1.data.entity.UserDto
import com.madhu.projectkapp1.ui.components.BackTopBar
import com.madhu.projectkapp1.ui.components.CommonButton
import com.madhu.projectkapp1.ui.components.ImageUploadField
import com.madhu.projectkapp1.ui.components.TextFieldTypeOne
import com.madhu.projectkapp1.ui.components.showSnackBar
import com.madhu.projectkapp1.ui.navigation.Screen
import com.madhu.projectkapp1.ui.theme.StaatlichesFontFamily
import com.madhu.projectkapp1.ui.viewmodel.UploadImageViewModel
import com.madhu.projectkapp1.ui.viewmodel.UserViewModel
import com.madhu.projectkapp1.utility.ResourceState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUser(
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel(),
    uploadImageViewModel: UploadImageViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var qrCodeUri by remember { mutableStateOf<Uri?>(null) }
    var qrCodeUrl by remember { mutableStateOf("") }
    var profileUri by remember { mutableStateOf<Uri?>(null) }
    var profileUrl by remember { mutableStateOf("") }

    var isInProgess by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var isFilled by remember { mutableStateOf(false) }
    var isQrUploaded by remember { mutableStateOf(false) }

    isFilled =
        qrCodeUri != null && profileUri != null
                && password1 == password2
                && fullName.isNotEmpty() && phoneNumber.isNotEmpty()
                && email.contains("@gmail.com") && password1.length > 7

    val tag = "CreateUser"

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }


    val imageResponse by uploadImageViewModel.uploadResponse.collectAsState()

    LaunchedEffect(imageResponse) {
        if (qrCodeUrl == "") {
            when (imageResponse) {

                is ResourceState.Success -> {
                    qrCodeUrl = (imageResponse as ResourceState.Success<JwtResponse>).data.jwtToken
                }

                else -> {}
            }

        }
        if (qrCodeUrl != "" && profileUrl == "" && isInProgess) {
            when (imageResponse) {

                is ResourceState.Success -> {
                    val response =
                        (imageResponse as ResourceState.Success<JwtResponse>).data.jwtToken
                    if (qrCodeUrl != response) {
                        profileUrl = response
                    }
                }

                else -> {}
            }

        }
    }


    Scaffold(
        topBar = {
            BackTopBar(navController = navController, route = Screen.Login.route)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            ImageUploadField(
                text = "Choose Profile Photo *",
                contentDescription = "Profile Image"
            ) {
                profileUri = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            ImageUploadField(
                text = "Choose QR Photo *",
                contentDescription = "QR Image"
            ) {
                qrCodeUri = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextFieldTypeOne(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name *",
                focusManager = focusManager,
            )

            TextFieldTypeOne(
                value = email,
                onValueChange = { email = it },
                label = "Email *",
                focusManager = focusManager,
            )

            TextFieldTypeOne(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },
                label = "Phone Number *",
                focusManager = focusManager,
                isNumber = true
            )

            phoneNumber = phoneNumber.replace(' ', '-')

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldTypeOne(
                value = password1,
                onValueChange = { password1 = it },
                label = "Password *",
                sideText = "min 8 characters",
                focusManager = focusManager,
                isPassword = true,
                description = "enter password"
            )

            TextFieldTypeOne(
                value = password2,
                onValueChange = { password2 = it },
                label = "Confirm Password",
                focusManager = focusManager,
                isPassword = true,
                isDone = true
            )

            Spacer(modifier = Modifier.height(28.dp))

            val userDto = UserDto(
                email = email,
                fullName = fullName,
                password = password1,
                phoneNumber = phoneNumber
            )

            if (qrCodeUrl != "" && profileUrl == "" && isInProgess && !isQrUploaded) {
                Log.d(tag, "Inside setting qr code")
                userDto.qrImageUrl = qrCodeUrl
                uploadImageViewModel.uploadImageToCloudinary(profileUri!!, context)
                isInProgess = true
                isQrUploaded = true
            }

            if (qrCodeUrl != "" && profileUrl != "" && isInProgess && isQrUploaded) {
                userDto.profileImageUrl = profileUrl
                userDto.qrImageUrl = qrCodeUrl

                Log.d(tag, "Adding User with $userDto")
                userViewModel.addUser(userDto)
                showSnackBar(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    navController = navController,
                    message = "$fullName User Added ",
                    delay = 3000
                )
                isInProgess = false
            }

            CommonButton(
                buttonText = "Create User",
                isFilled = isFilled && !isInProgess,
                description = "create user using specified information",
                onClick = {
                    isFilled = false
                    if (!isInProgess)
                        isInProgess = true

                    if (qrCodeUri != null) {
                        uploadImageViewModel.uploadImageToCloudinary(qrCodeUri!!, context)
                        Log.d(tag, "After Calling Uploading")
                    } else {
                        Log.d(tag, "Uri Not Selected")
                        userViewModel.addUser(userDto)

                        showSnackBar(
                            scope = scope,
                            snackbarHostState = snackbarHostState,
                            navController = navController,
                            message = "User Added"
                        )
                    }

                }
            )


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    isPassword: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    focusManager: FocusManager,
    imeAction: ImeAction = ImeAction.Next,
    isNumber: Boolean = false

) {

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        label = { Text(title) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp))
            .semantics {
                contentDescription = title
            }
            .background(Color.White) // Set the background color
            .padding(8.dp),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontFamily = StaatlichesFontFamily
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            },
            onDone = {
                focusManager.clearFocus(true)
            }

        ),
    )

}