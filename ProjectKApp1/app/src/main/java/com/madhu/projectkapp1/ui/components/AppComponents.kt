package com.madhu.projectkapp1.ui.components


import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.madhu.projectkapp1.R
import com.madhu.projectkapp1.data.enums.SearchWidgetState
import com.madhu.projectkapp1.ui.theme.EczarFontFamily
import com.madhu.projectkapp1.ui.theme.bungeeFontFamily
import com.madhu.projectkapp1.ui.theme.kdamFontFamily
import com.madhu.projectkapp1.ui.viewmodel.CustomerViewModel
import com.madhu.projectkapp1.ui.viewmodel.ProductViewModel
import com.madhu.projectkapp1.ui.viewmodel.VillageViewModel
import com.madhu.projectkapp1.utility.IdType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(48.dp)
                .semantics {
                    contentDescription =
                        "Loading... Please wait while we retrieve the latest data for you."
                }
        )
    }


}

@Composable
fun DisplayMapElements(
    title: String,
    modifier: Modifier = Modifier,
    map: Map<String, String>,
    backgoundColor: Color = Color.LightGray
) {

    var showCard by remember { mutableStateOf(false) }


    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .animateContentSize()
            .clickable {
                showCard = !showCard
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgoundColor
        ),
        elevation = CardDefaults.elevatedCardElevation()

    ) {

        DisplayTitle(title = title)

        if (showCard) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                map.forEach { (key, value) ->
                    SideBySideText(key = key, value = value)
                }
            }
        }
    }
}


@Composable
fun ImageUploadField(
    defaultPainter: Painter = painterResource(id = R.drawable.default_customer),
    text: String = "Choose Photo",
    contentDescription: String,
    onUriSelection: (Uri?) -> Unit
) {
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }
    LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            pickedImageUri = uri
        }
    }

    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        // Display the picked image or a default image
        if (pickedImageUri != null) {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(
                    LocalContext.current
                )
                    .data(pickedImageUri)
                    .crossfade(true)
                    .transformations(
                        CircleCropTransformation()
                    )
                    .build()
            )

            Image(
                painter = painter,
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = CircleShape)
                    .background(Color.Gray)
                    .graphicsLayer {
                        shape = CircleShape
                    },
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,

                )
        } else {

            Image(
                painter = defaultPainter,
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = CircleShape)
                    .graphicsLayer {
                        shape = CircleShape
                    },
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,

                )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                launcher.launch("image/*")
            }
        ) {
            Text(text)
        }
    }
    onUriSelection(pickedImageUri)
}


@Composable
fun MainAppBar(
    defaultAppBarTitle: String,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
) {

    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(title = defaultAppBarTitle, onSearchTriggered)
        }

        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    title: String,
    onSearchClicked: () -> Unit
) {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                onSearchClicked()
            },
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(top = 2.dp),
                fontFamily = bungeeFontFamily
            )
        },
        actions = {
            IconButton(onClick = { onSearchClicked() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search icon",
                    tint = Color.White
                )

            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.LightGray
        ),
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {

        TextField(
            value = text,
            onValueChange = { onTextChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search Here",
                    color = Color(0xFF4B00E3),
                    fontSize = 16.sp,
                    fontFamily = kdamFontFamily
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF0022FF).copy(alpha = 0.8f),
                containerColor = Color.LightGray
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                fontFamily = kdamFontFamily,
                fontWeight = FontWeight.Bold
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.6f),
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),


            )
    }

}


@Composable
fun DeleteConfirmationScreen(
    id: Int,
    idType: IdType,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    customerViewModel: CustomerViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    villageViewModel: VillageViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

    val tag = "DeleteConfirmationScreen "

    val text: String = when (idType) {
        IdType.CUSTOMER_ID -> "Customer"
        IdType.VILLAGE_ID -> "Village"
        IdType.PRODUCT_ID -> "Product"
    }

    Column {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(Icons.Default.Delete, contentDescription = "delete")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Delete")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(
                        text = "Confirm Deletion",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp
                    )
                },
                text = {
                    Text(text = "Delete $text ?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            Log.d(tag, "Deleting $text")
                            showDialog = false

                            when (idType) {

                                IdType.CUSTOMER_ID -> {
                                    customerViewModel.deleteCustomer(id)
                                }

                                IdType.PRODUCT_ID -> {
                                    productViewModel.deleteProduct(id)

                                }

                                IdType.VILLAGE_ID -> {
                                    villageViewModel.deleteVillage(id)

                                }

                            }
                            Log.d(tag, "Showing SnackBar")
                            showSnackBar(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                                navController = navController,
                                message = "$text Deleted"
                            )
                            scope.launch {
                                delay(1500)
                                navController.popBackStack()
                            }


                        }
                    ) {
                        Text(text = "Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }
}


@Composable
fun AnimatedRetryButton(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRotated by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 360f else 0f,
        tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ), label = "rotate"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
            ) {

                isRotated = !isRotated
                onRetry()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "refresh icon",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.rotate(rotationAngle)
        )
    }
}


fun createFileFromUri(context: Context, uri: Uri?): File? {
    if (uri == null) {
        return null
    }

    val contentResolver: ContentResolver = context.contentResolver
    val displayName: String = getDisplayName(contentResolver, uri)

    // Create a file with a unique name
    val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val file = File(directory, displayName)

    // Copy data from the content resolver to the file
    contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    return file
}

@SuppressLint("Range")
private fun getDisplayName(contentResolver: ContentResolver, uri: Uri): String {
    var displayName: String? = null

    // Try to get the display name from the content resolver
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    }

    // If the display name is null, use a default name
    if (displayName == null) {
        displayName = "file_${System.currentTimeMillis()}"
    }

    return displayName!!
}


@Composable
fun GalleryButton(
    onUriSelection: (Uri) -> Unit,
    buttonText: String
) {
    val openGallery = rememberGalleryLauncher(onUriSelection)

    Button(
        onClick = { openGallery() },
    ) {
        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(buttonText)
    }
}

@Composable
fun rememberGalleryLauncher(
    onUriSelection: (Uri) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    onUriSelection(uri)
                }
            }
        }

    return {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        getContent.launch(intent)
    }
}


@Composable
fun PickSingleImage(
    onImagePicked: (Uri?) -> Unit, // Callback for the picked image URI
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Use ActivityResultContracts.GetContent for Android 10 and above
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                onImagePicked(uri) // Pass the picked image URI to the callback
            }
        }

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Pick Image")
        }
    }
}
