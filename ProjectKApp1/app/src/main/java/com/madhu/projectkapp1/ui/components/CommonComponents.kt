package com.madhu.projectkapp1.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.madhu.projectkapp1.R
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.enums.Occassion
import com.madhu.projectkapp1.ui.navigation.AUTHENTICATION_ROUTE
import com.madhu.projectkapp1.ui.theme.CustomColors
import com.madhu.projectkapp1.ui.theme.EczarFontFamily
import com.madhu.projectkapp1.ui.theme.FiraMonoFontFamily
import com.madhu.projectkapp1.ui.theme.OxygenMonoFontFamily
import com.madhu.projectkapp1.ui.theme.Purple40
import com.madhu.projectkapp1.ui.theme.francoisFontFamily
import com.madhu.projectkapp1.ui.theme.josephFontFamily
import com.madhu.projectkapp1.ui.theme.kdamFontFamily
import com.madhu.projectkapp1.ui.theme.vinaSansFontFamily
import com.madhu.projectkapp1.ui.viewmodel.LoginViewModel
import com.madhu.projectkapp1.utility.DefaultImageType
import com.madhu.projectkapp1.utility.MODE_OF_PAYMENT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import kotlin.random.Random


@Composable
fun DefaultImage(defaultPainter: Painter) {
    Image(
        painter = defaultPainter,
        contentDescription = "Default Profile Picture",
        modifier = Modifier.clip(shape = RoundedCornerShape(100.dp))
    )
}


@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    imageColor: Int = Color.Green.toArgb(),
    size: Dp = 154.dp,
    borderWidth: Dp = 4.dp,
    defaultPainter: Painter? = null,
    showBorder: Boolean = true
) {


    Box(
        modifier = modifier
            .padding(16.dp)
            .size(size)
            .clip(RoundedCornerShape(4.dp))
            .then(
                if (showBorder) {
                    modifier.border(
                        color = Color(imageColor),
                        width = borderWidth,
                        shape = RoundedCornerShape(size)
                    )
                } else {
                    modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {

        if (imageUrl == null && defaultPainter != null) {
            DefaultImage(defaultPainter)
        }

        if (imageUrl != null) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(
                    LocalContext.current
                )
                    .data(imageUrl)
                    .crossfade(true)
                    .transformations(
                        CircleCropTransformation()
                    )
                    .build()
            )
            Image(
                painter = painter,
                modifier = Modifier.fillMaxSize(0.9f),
                contentDescription = "Profile Picture"
            )

            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
fun SideBySideText(
    key: String,
    value: String,
    leftColor: Color = MaterialTheme.colorScheme.primary,
    rightColor: Color = MaterialTheme.colorScheme.onSurface,
    leftFontSize: TextUnit = 16.sp,
    rightFontSize: TextUnit = 14.sp
) {

    Log.d("SideBySideText", "Inside Side By Side Text")


    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = leftColor,
                fontSize = leftFontSize,
                fontWeight = FontWeight.SemiBold,
                fontFamily = OxygenMonoFontFamily
            )
        ) {
            append("$key : ")
        }
        withStyle(
            style = SpanStyle(
                color = rightColor,
                fontWeight = FontWeight.Normal,
                fontFamily = EczarFontFamily,
                fontSize = rightFontSize
            )
        ) {
            append(value)
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier.fillMaxWidth(),
        overflow = TextOverflow.Ellipsis

    )
}


@Composable
fun GenericItem(
    modifier: Modifier = Modifier,
    elements: List<String?>,
    imageUrl: String? = null,
    colors: List<Color> =
        listOf(
            CustomColors.DarkBlue, CustomColors.DarkGreen, CustomColors.IndianRed,
            CustomColors.DarkCyan, CustomColors.Azure, CustomColors.Beige,
            CustomColors.Linen, CustomColors.SpringGreen
        ),
    navController: NavHostController,
    route: String,
    borderWidth: Dp = 4.dp,
    defaultImageType: DefaultImageType? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface
) {

    val tag = "GenericItem"

    val filteredElements = elements.filterNotNull()

    val defalutPainter: Painter? = when (defaultImageType) {
        DefaultImageType.PRODUCT -> {
            painterResource(id = R.drawable.default_product)
        }

        DefaultImageType.CUSTOMER -> {
            painterResource(id = R.drawable.default_customer)
        }

        DefaultImageType.VILLAGE -> {
            painterResource(id = R.drawable.default_village)
        }

        DefaultImageType.RECORD -> {
            painterResource(id = R.drawable.default_record)
        }

        else -> {
            null
        }
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 4.dp)
            .border(
                2.dp,
                Color.Red.copy(alpha = 0.3f),
                shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
            )
            .clickable {
                Log.d(tag, "the route is $route")
                navController.navigate(route = route)
            },
        shape = CardDefaults.elevatedShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
            pressedElevation = 12.dp,
            focusedElevation = 12.dp,
            hoveredElevation = 16.dp,
            draggedElevation = 20.dp
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            if (imageUrl != null || defaultImageType != null) {
                CoilImage(
                    imageUrl = imageUrl,
                    size = 40.dp,
                    borderWidth = borderWidth,
                    modifier = modifier,
                    defaultPainter = defalutPainter
                )
            }

            filteredElements.forEachIndexed { index, text ->
                Text(
                    modifier = modifier,
                    text = text,
                    fontSize = 16.sp,
                    color = colors[index],
                    fontWeight = if (index == 0) FontWeight.Medium else FontWeight.SemiBold,
                    fontFamily = francoisFontFamily
                )
            }
        }


    }


}

@Composable
fun NoInternetScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val images = listOf(
            R.drawable.ai_dog_one,
            R.drawable.ai_dog_two,
            R.drawable.ai_dog_three,
            R.drawable.ai_dog_four,
            R.drawable.ai_dog_five,
            R.drawable.ai_dog_six,
            R.drawable.ai_dog_seven,
            R.drawable.ai_dog_eight,
            R.drawable.ai_dog_nine,
            R.drawable.ai_dog_ten
        )

        val randomIndex = Random.nextInt(images.size)

        Image(
            painter = painterResource(id = images[randomIndex]),
            contentDescription = "dog",
            Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "No Internet Connection",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedRetryButton(onRetry = { navController.navigate(AUTHENTICATION_ROUTE) })

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ErrorScreen(message: String = "Something Went Wrong") {


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error Image",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = message,
            fontFamily = OxygenMonoFontFamily,
            maxLines = 2,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.background(Color.White),
            fontSize = 20.sp
        )


    }

}


@Composable
fun DisplayTitle(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = title,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 28.sp,
        fontFamily = vinaSansFontFamily,
        textAlign = TextAlign.Center
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    navController: NavHostController,
    screenTitle: String = "Back",
    route: String? = null
) {

    TopAppBar(
        title = {
            Text(
                text = screenTitle,
                fontWeight = FontWeight.SemiBold,
                fontFamily = josephFontFamily,
                modifier = Modifier.clickable {
                    if (route != null)
                        navController.navigate(route)
                    else
                        navController.popBackStack()
                }
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    if (route != null)
                        navController.navigate(route)
                    else
                        navController.popBackStack()
                },
                modifier = Modifier.semantics {
                    contentDescription = "Go Back"
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )

}


@Composable
fun AppLogoSection() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Image(
            painter = painterResource(id = R.drawable.app_top_bar_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(300.dp)
        )

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample(
    options: List<NameAndId>,
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit,
    onSelectedOptionIdChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {


            TextField(
                value = selectedOption,
                onValueChange = { onSelectedOptionChange(it) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                ) {

                options.forEach { option ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            onSelectedOptionChange(option.name)
                            onSelectedOptionIdChange(option.id)
                            expanded = false
                        },
                        text = { Text(text = option.name) }
                    )
                    //  Spacer(modifier = Modifier.height(4.dp))

                }

            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExampleForEnums(
    options: List<String>,
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {


            TextField(
                value = selectedOption,
                onValueChange = { onSelectedOptionChange(it) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                ) {

                options.forEach { option ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            onSelectedOptionChange(option)
                            expanded = false
                        },
                        text = { Text(text = option) }
                    )

                }

            }


        }
    }
}


@Composable
fun HandleOccassionDropDown(
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit
) {

    val occasionList: List<Occassion> = enumValues<Occassion>().toList()

    val stringOccasionList =
        occasionList.stream().map { it.toString() }.collect(Collectors.toList())

    DropdownMenuExampleForEnums(
        options = stringOccasionList,
        selectedOption = selectedOption,
        onSelectedOptionChange = onSelectedOptionChange,
    )

}


@Composable
fun HandleModeOfPaymentDropDown(
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit
) {

    val paymentModes: List<MODE_OF_PAYMENT> = enumValues<MODE_OF_PAYMENT>().toList()

    val stringPaymentModeList =
        paymentModes.stream().map { it.toString() }.collect(Collectors.toList())

    DropdownMenuExampleForEnums(
        options = stringPaymentModeList,
        selectedOption = selectedOption,
        onSelectedOptionChange = onSelectedOptionChange,
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction = ImeAction.Next,
    focusManager: FocusManager,
    focusDirection: FocusDirection = FocusDirection.Next,
    isMandatory: Boolean = false,
    isNumber: Boolean = false,
    description: String = "button"

) {

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(focusDirection)
            },
            onDone = {
                focusManager.clearFocus(true)
            }

        ), colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = if (isMandatory) Color(0xFF1E88E5)
            else Color(0xFFAB0000),
            containerColor = Color.White
        ),
        maxLines = 1,
        modifier = Modifier.semantics {
            contentDescription = description
        }

    )

}


@Composable
fun AlertWithInputValues(
    onDismiss: (Boolean) -> Unit,
    onConfirm: (Boolean) -> Unit,
    amount: String,
    onAmountChange: (String) -> Unit,
    mode: String,
    onModeChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = { onDismiss(false) },
        title = {
            Text("Add Transaction")
        },
        text = {
            Column {

                CommonOutlineTextField(
                    value = amount,
                    onValueChange = { onAmountChange(it.filter { char -> char.isDigit() }) },
                    label = "Enter Amount",
                    focusManager = focusManager,
                    isNumber = true
                )

                HandleModeOfPaymentDropDown(
                    selectedOption = mode,
                    onSelectedOptionChange = { onModeChange(it) }
                )



                CommonOutlineTextField(
                    value = description,
                    onValueChange = { onDescriptionChange(it) },
                    label = "Description",
                    focusManager = focusManager,
                    imeAction = ImeAction.Done
                )

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(false)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss(false) },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val tag = "MyTopAppBar"
    TopAppBar(
        title = {
            AppLogoSection()
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        actions = {
            IconButton(
                onClick = {
                    Log.d(tag, "Logout clicked")
                    loginViewModel.clearTokenFromStorage()
                    navController.navigate(AUTHENTICATION_ROUTE) {
                        popUpTo(AUTHENTICATION_ROUTE) {
                            inclusive = false
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.logout),
                    contentDescription = "Logout"
                )

            }
        }

    )

}


@SuppressLint("UnrememberedMutableState")
@Composable
fun MyFloatingBar(
    navController: NavHostController,
    scrollState: LazyListState,
    route: String,
    title: String
) {


    val isScrolled by derivedStateOf {
        scrollState.firstVisibleItemIndex > 0 || scrollState.isScrollInProgress
    }


    FloatingActionButton(
        modifier = Modifier.padding(16.dp),
        onClick = {
            navController.navigate(route)
        },
        contentColor = MaterialTheme.colorScheme.tertiary,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize()
        ) {
            //  Log.d(tag,"The scroll is ${remember { derivedStateOf { scrollState.firstVisibleItemIndex } }}")

            Icon(
                imageVector = Icons.Default.Add, contentDescription = title,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(top = 3.dp)

            )
            if (!isScrolled) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(4.dp),
                    fontFamily = francoisFontFamily

                )
            }
        }


    }

}


fun showSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    message: String,
    delay: Long = 1000
) {
    scope.launch {
        delay(delay)

        val response = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "Done",
            duration = SnackbarDuration.Long
        )

        when (response) {
            SnackbarResult.ActionPerformed -> {
                navController.popBackStack()
            }

            else -> {}
        }

    }
}


@Composable
fun CommonButton(
    buttonText: String,
    isFilled: Boolean,
    onClick: () -> Unit,
    description: String = "",
) {
    ElevatedButton(
        enabled = isFilled,
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(
            bottomStart = 16.dp,
            topEnd = 16.dp
        ),
        modifier = Modifier
            .height(54.dp)
            .semantics { contentDescription = if(description=="") buttonText else description },
        elevation = ButtonDefaults.elevatedButtonElevation(),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Purple40
        )


    ) {
        Text(
            text = buttonText,
            fontFamily = FiraMonoFontFamily,
            fontSize = 20.sp,
            color = if (isFilled) Color.White else Color.Black
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldTypeOne(
    value: String,
    label: String,
    sideText:String="",
    description: String="",
    isPassword: Boolean = false,
    isDone: Boolean = false,
    isNumber: Boolean = false,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {

    var showPassword by remember { mutableStateOf(!isPassword) }

    val currentView = LocalView.current

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Row {
            Text(label)
            Text(sideText,modifier = Modifier.padding(8.dp), fontSize = 12.sp)
        }  },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp))
            .semantics {
                contentDescription = if(description=="") label else description
            },
        visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontFamily = kdamFontFamily,
            fontWeight = FontWeight.Bold
        ),
        trailingIcon = {
            if (isPassword) {
                IconButton(
                    onClick = {
                        showPassword = !showPassword
                        currentView.requestFocus()
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    val image = if (showPassword) {
                        painterResource(id = R.drawable.view)
                    } else {
                        painterResource(id = R.drawable.hide)
                    }
                    Icon(painter = image, contentDescription = "display "+ if(description=="") label else description)
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = if (isDone) ImeAction.Done else ImeAction.Next,
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            },
            onDone = {
                focusManager.clearFocus(true)
            }
        )
    )

}
