package com.example.camerademo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FetchImageActivity : AppCompatActivity() {

    companion object {

        lateinit var currentFilePath: String
        lateinit var photoUri: Uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            val imageFile: File? = try {
                createImageFile()
            } catch (e: IOException) {
                null
            }
            imageFile?.also {
                photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.android.fileprovider",
                    it
                )
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentFilePath)
                takePictureLauncher.launch(photoUri)

            }
        }

    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            Log.i("take picture", "got image at uri: $currentFilePath")
            val returnIntent = Intent()
            returnIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentFilePath)
            setResult(RESULT_OK, returnIntent)
            Log.d("returned intent", "$returnIntent")
            finish()

        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp: String = "${SimpleDateFormat("yyyyMMdd")}".format(Date())
        val fileStoragePath: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            fileStoragePath
        )
        imageFile.apply {
            currentFilePath = absolutePath
        }
        return imageFile
    }

}