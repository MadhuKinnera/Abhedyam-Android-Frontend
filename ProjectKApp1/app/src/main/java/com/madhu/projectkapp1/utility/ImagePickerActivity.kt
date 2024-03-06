package com.madhu.projectkapp1.utility

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.io.FileOutputStream

class ImagePickerActivity(
    context: Context
) : ComponentActivity() {

    private lateinit var launcher2: ActivityResultLauncher<Intent>

    private val launcher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            context.contentResolver?.openInputStream(it)?.use { inputStream ->
                val tempFile = File.createTempFile("image", ".jpg", context?.cacheDir)
                val outputStream = FileOutputStream(tempFile)

                inputStream.copyTo(outputStream)
                outputStream.close()

                // Use the temporary file for image processing
                setContent {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = File(tempFile.path)
                        ),
                        contentDescription = "Downloaded Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        } else {
            pickImage()
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        launcher2.launch(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage()
        }
    }

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 101
    }
}
