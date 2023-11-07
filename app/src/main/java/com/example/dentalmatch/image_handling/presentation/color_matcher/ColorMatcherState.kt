package com.example.dentalmatch.image_handling.presentation.color_matcher

import com.example.dentalmatch.upload_image.domain.ColorCodeModel

sealed class ColorMatcherState {

    data class SuccessColorsList(val colorsList: List<ColorCodeModel>) : ColorMatcherState()
    data class Error(val message: String) : ColorMatcherState()
    data object Idle : ColorMatcherState()
}
