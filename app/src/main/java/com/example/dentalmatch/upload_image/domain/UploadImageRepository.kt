package com.example.dentalmatch.upload_image.domain

import com.example.dentalmatch.common.util.Resource
import kotlinx.coroutines.flow.Flow

interface UploadImageRepository {

    suspend fun insertNotes(colorCodeModel: ColorCodeModel)

    fun getAllColors(): Flow<Resource<List<ColorCodeModel>>>

    suspend fun deleteColor(colorCodeModel: ColorCodeModel)

}