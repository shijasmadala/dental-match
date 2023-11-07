package com.example.dentalmatch.upload_image.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dentalmatch.upload_image.data.entity.ColorCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addColor(colorCodeEntity: ColorCodeEntity)

    @Query("SELECT * FROM ColorCodeEntity")
    fun getAllColors(): Flow<List<ColorCodeEntity>>
}