package com.example.dentalmatch.upload_image.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalmatch.common.util.Resource
import com.example.dentalmatch.home.presentation.HomeState
import com.example.dentalmatch.upload_image.domain.ColorCodeModel
import com.example.dentalmatch.upload_image.domain.UploadImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadImageVieModel @Inject constructor(private val uploadImageRepository: UploadImageRepository) :
    ViewModel() {

    private val _addImageState = Channel<UploadImageState>()
    val addImageState = _addImageState.receiveAsFlow()

    private val _state = MutableStateFlow<UploadImageState>(UploadImageState.Empty)
    val state = _state.asStateFlow()

    init {
        getAllColorsList()
    }


    fun addColor(colorCodeModel: ColorCodeModel) = viewModelScope.launch(Dispatchers.IO) {
        when {
            colorCodeModel.teethCode.isNullOrBlank() -> {
                _state.emit(UploadImageState.Error("Please enter the code"))
            }

            colorCodeModel.color == null -> {
                _state.emit(UploadImageState.Error("Provide color"))
            }

            else -> {
                kotlin.runCatching {
                    uploadImageRepository.insertNotes(colorCodeModel)
                }.onSuccess {
                    _state.emit(UploadImageState.UploadImageSuccess("Added"))
                }.onFailure {

                }
            }
        }
    }

    fun getAllColorsList() = viewModelScope.launch {
        uploadImageRepository.getAllColors().collectLatest {
            when (it) {
                is Resource.Success -> _state.emit(
                    UploadImageState.SuccessColorsList(it.value)
                )
                is Resource.Error -> _state.emit(
                    UploadImageState.Error(it.error)
                )
                else -> Unit
            }
        }
    }

    fun deleteColor(colorCodeModel: ColorCodeModel) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
         uploadImageRepository.deleteColor(colorCodeModel)
        }.onSuccess {
            _state.emit(UploadImageState.DeleteColor("Deleted"))
        }
    }

}