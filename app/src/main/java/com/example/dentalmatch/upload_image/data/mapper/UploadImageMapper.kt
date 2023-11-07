package com.example.dentalmatch.upload_image.data.mapper

import com.example.dentalmatch.upload_image.data.entity.ColorCodeEntity
import com.example.dentalmatch.upload_image.domain.ColorCodeModel

fun ColorCodeModel.toEntity(): ColorCodeEntity {
    return ColorCodeEntity(
        id,
        teethCode = teethCode,
        color
    )
}

fun ColorCodeEntity.toColorCodeModel(): ColorCodeModel {
    return ColorCodeModel(
        id = id,
        teethCode = teethCode,
        color = color
    )
}
