package com.example.dentalmatch.upload_image.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalmatch.add_patient.presentation.AddPatientState
import com.example.dentalmatch.upload_image.domain.ColorCodeModel
import com.example.dentalmatch.upload_image.domain.UploadImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UploadImageVieModel @Inject constructor(private val uploadImageRepository: UploadImageRepository) :
    ViewModel() {

    private val _addImageState = Channel<UploadImageState>()
    val addImageState = _addImageState.receiveAsFlow()
    fun addColor(colorCodeModel: ColorCodeModel) = viewModelScope.launch(Dispatchers.IO) {
        when {
            colorCodeModel.teethCode.isNullOrBlank() -> {
                _addImageState.send(UploadImageState.Error("Please enter the code"))
            }
            colorCodeModel.color == null -> {
                _addImageState.send(UploadImageState.Error("Provide color"))
            }

            else -> {
                kotlin.runCatching {
                    uploadImageRepository.insertNotes(colorCodeModel)
                }.onSuccess {
                    _addImageState.send(UploadImageState.UploadImageSuccess("Added"))
                }.onFailure {

                }
            }
        }
    }
}