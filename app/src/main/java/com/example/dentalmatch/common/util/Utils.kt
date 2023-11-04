package com.example.dentalmatch.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

fun File.toBitmap(): Bitmap = BitmapFactory.decodeFile(
    absolutePath, BitmapFactory.Options()
)

fun Int.toHexColor(): String = "#${String.format("%06X", 0xFFFFFF and this)}"