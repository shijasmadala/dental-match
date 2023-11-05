package com.example.dentalmatch.upload_image.data.repository

import com.example.dentalmatch.upload_image.data.source.ColorCodeDao
import com.example.dentalmatch.upload_image.domain.ColorCodeModel
import com.example.dentalmatch.upload_image.domain.UploadImageRepository
import com.example.dentalmatch.upload_image.toEntity
import javax.inject.Inject

class UploadImageRepositoryImpl @Inject constructor(private val colorCodeDao: ColorCodeDao) : UploadImageRepository {
    override suspend fun insertNotes(colorCodeModel: ColorCodeModel) {
        kotlin.runCatching {
            colorCodeDao.addColor(colorCodeModel.toEntity())
        }.onSuccess {

        }.onFailure {

        }
    }
}