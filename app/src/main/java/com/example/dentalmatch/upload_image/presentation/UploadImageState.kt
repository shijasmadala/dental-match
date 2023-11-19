package com.example.dentalmatch.upload_image.presentation

import com.example.dentalmatch.home.presentation.HomeState
import com.example.dentalmatch.image_handling.presentation.color_matcher.ColorMatcherState
import com.example.dentalmatch.upload_image.domain.ColorCodeModel

sealed class UploadImageState {
    data class UploadImageSuccess(
        val message: String,

        ) : UploadImageState()

    data class UpdateUploadImageSuccess(
        val message: String,

        ) : UploadImageState()

    data class DeleteColor(val message: String) : UploadImageState()

    data class SuccessColorsList(val colorsList: List<ColorCodeModel>) : UploadImageState()

    data class Error(val message: String) : UploadImageState()
    data object Loading : UploadImageState()
    data object Empty : UploadImageState()
}
