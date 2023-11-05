package com.example.dentalmatch.upload_image.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ColorCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val teethCode : String,
    val color : Int
)
