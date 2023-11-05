package com.example.dentalmatch.upload_image.presentation

sealed class UploadImageState {
    data class UploadImageSuccess(
        val message: String,

        ) : UploadImageState()

    data class UpdateUploadImageSuccess(
        val message: String,

        ) : UploadImageState()

    data class Error(val message: String) : UploadImageState()
    data object Loading : UploadImageState()
    data object Empty : UploadImageState()
}
