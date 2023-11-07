package com.example.dentalmatch.image_handling.presentation.color_matcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalmatch.common.util.Resource
import com.example.dentalmatch.upload_image.domain.UploadImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorMatcherViewModel @Inject constructor(
    private val colorRepository: UploadImageRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ColorMatcherState>(ColorMatcherState.Idle)
    val state = _state.asStateFlow()

    init {
        getAllColorsList()
    }

    private fun getAllColorsList() = viewModelScope.launch {
        colorRepository.getAllColors().collectLatest {
            when (it) {
                is Resource.Success -> _state.emit(
                    ColorMatcherState.SuccessColorsList(it.value)
                )
                is Resource.Error -> _state.emit(
                    ColorMatcherState.Error(it.error)
                )
                else -> Unit
            }
        }
    }

}
