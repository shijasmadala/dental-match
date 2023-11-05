package com.example.dentalmatch.upload_image.domain

interface UploadImageRepository {
    suspend fun insertNotes(colorCodeModel: ColorCodeModel)

}