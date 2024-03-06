package com.madhu.projectkapp1.utility

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


object CoreUtility {

    const val tag = "CoreUtility"

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false

        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        val result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }


        return result
    }


//    @SuppressLint("Recycle")
//    fun uriToFile(context: Context, uri: Uri?): File? {
//        Log.d(tag, "Uri is $uri")
//        if (uri == null)
//            return null
//        val contentResolver = context.contentResolver
//        val tempFile: File? = try {
//            val tempFile = File.createTempFile("profile", ".jpg", context.externalCacheDir)
//            tempFile.deleteOnExit()
//            tempFile.outputStream().use { outputStream ->
//                contentResolver.openInputStream(uri)?.copyTo(outputStream)
//            }
//            tempFile
//        } catch (e: Exception) {
//            null
//        }
//        return tempFile
//    }


    fun trimRoute(input: String): String {
        val index = input.indexOf('/')
//        Log.d("GenericItem","index of / is $index and now string is ${input.substring(0,index+1)}")
        return if (index != -1) {
            input.substring(0, index + 1)
        } else {
            input
        }
    }


    fun uploadToCloudinary(
        context: Context,
        imageUri: Uri
    ): MultipartBody.Part {

        Log.d(tag,"Image uri $imageUri")
        // val file = uriToFile(context, imageUri)
           val file = uriToFile(context, imageUri)

       // val file = imageUri.asFile(context)
        Log.d(tag, "File name is ${file?.name}")

        val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())

        Log.d(tag, "Request Body $requestBody")

        val multipartBody =
            requestBody?.let { MultipartBody.Part.createFormData("image", file?.name?:"image.jpg", it) }
        Log.d(tag, "Request Body $multipartBody")


        return multipartBody!!

    }


}


@SuppressLint("Recycle")
fun uriToFile(context: Context, uri: Uri?): File? {
    if (uri == null) return null
    val contentResolver = context.contentResolver

    try {
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val file = File.createTempFile("image", ".jpg", context.cacheDir)
        val outputStream = file.outputStream()

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}


fun Uri.asFile(
    context: Context
): File? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            val tempFile = File.createTempFile("image", ".jpg", context.cacheDir)
            val outputStream = tempFile.outputStream()
            inputStream.copyTo(outputStream)
            return tempFile
        }
    } else {
        // Use deprecated method for older versions
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                if (imagePath.isNotEmpty()) {
                    return File(imagePath)
                } else {
                    // Fallback to deprecated method for specific cases
                    return returnCursorData(this, context)?.let { File(it) }
                }
            }
            return null
        }
    }
    return null
}

private fun returnCursorData(uri: Uri?, context: Context): String? {


    if (DocumentsContract.isDocumentUri(context, uri)) {
        val wholeID = DocumentsContract.getDocumentId(uri)
        val splits = wholeID.split(":".toRegex()).toTypedArray()
        if (splits.size == 2) {
            val id = splits[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor: Cursor? = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            val columnIndex: Int? = cursor?.getColumnIndex(column[0])
            if (cursor?.moveToFirst() == true) {
                return columnIndex?.let { cursor.getString(it) }
            }
            cursor?.close()
        }
    } else {
        return uri?.path
    }
    return null
}
