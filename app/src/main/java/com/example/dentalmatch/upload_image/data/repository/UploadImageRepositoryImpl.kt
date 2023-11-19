package com.example.dentalmatch.upload_image.data.repository

import com.example.dentalmatch.common.util.Constants.EMPTY_COLORS
import com.example.dentalmatch.common.util.Resource
import com.example.dentalmatch.upload_image.data.mapper.toColorCodeModel
import com.example.dentalmatch.upload_image.data.mapper.toEntity
import com.example.dentalmatch.upload_image.data.source.ColorCodeDao
import com.example.dentalmatch.upload_image.domain.ColorCodeModel
import com.example.dentalmatch.upload_image.domain.UploadImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadImageRepositoryImpl @Inject constructor(
    private val colorCodeDao: ColorCodeDao
) : UploadImageRepository {

    override suspend fun insertNotes(colorCodeModel: ColorCodeModel) {
        kotlin.runCatching {
            colorCodeDao.addColor(colorCodeModel.toEntity())
        }
            .onSuccess {}
            .onFailure {}
    }

    override fun getAllColors(): Flow<Resource<List<ColorCodeModel>>> = flow {
        val colorsEntityList = colorCodeDao.getAllColors().first()
        if (colorsEntityList.isNotEmpty()) {
            val colorsList = colorsEntityList.map { it.toColorCodeModel() }
            emit(Resource.Success(colorsList))
        } else emit(Resource.Error(EMPTY_COLORS))
    }

    override suspend fun deleteColor(colorCodeModel: ColorCodeModel) {
        kotlin.runCatching {
            colorCodeDao.deleteColor(colorCodeModel.toEntity())
        }
    }
}