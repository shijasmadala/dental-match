package com.example.dentalmatch.upload_image.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.dentalmatch.upload_image.data.entity.ColorCodeEntity

@Dao
interface ColorCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addColor(colorCodeEntity: ColorCodeEntity)
}