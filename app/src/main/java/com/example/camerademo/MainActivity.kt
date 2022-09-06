package com.example.camerademo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class MainActivity : AppCompatActivity() {


    private val imageBtn: Button
        get() = findViewById(R.id.imageBtn)

    private val image: ImageView
        get() = findViewById(R.id.image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageBtn.setOnClickListener {
            val intent = Intent(this, FetchImageActivity::class.java)
            fetchImageLauncher.launch(intent)

        }


    }

    private val fetchImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val imagePath: String? = it.data?.getStringExtra(MediaStore.EXTRA_OUTPUT)
            val bitmap: Bitmap = BitmapFactory.decodeFile(imagePath)
            image.setImageBitmap(bitmap)

        }
}