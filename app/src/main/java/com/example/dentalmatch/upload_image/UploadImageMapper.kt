package com.example.dentalmatch.upload_image

import com.example.dentalmatch.upload_image.data.entity.ColorCodeEntity
import com.example.dentalmatch.upload_image.domain.ColorCodeModel

fun ColorCodeModel.toEntity (): ColorCodeEntity{
    return ColorCodeEntity(
        id,
        colorCode = colorCode,
        color
    )
}