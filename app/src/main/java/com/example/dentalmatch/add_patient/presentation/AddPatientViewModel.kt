package com.example.dentalmatch.add_patient.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.add_patient.domain.repository.AddPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor(
    private val addPatientRepository: AddPatientRepository,
    private val savedStateHandle: SavedStateHandle,
) :
    ViewModel() {
    private val patientModel =savedStateHandle.get<PatientModel>("patientData")

    private val _addPatientState = Channel<AddPatientState>()
    val addPatientState = _addPatientState.receiveAsFlow()

    init {
        viewModelScope.launch {
            _addPatientState.send(AddPatientState.UpdatePatientData(patientModel))
        }
    }

    fun addPatient(patientModel: PatientModel) = viewModelScope.launch(Dispatchers.IO) {
        when {
            patientModel.patientName.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please enter the name"))
            }

            patientModel.age.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please enter the age"))
            }

            patientModel.gender.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please select the gender"))
            }

            patientModel.toothNumber == 0 -> {
                _addPatientState.send(AddPatientState.Error("Please enter the tooth no"))
            }

            patientModel.toothCode.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please upload the tooth code"))
            }

            else -> {
                kotlin.runCatching {
                    addPatientRepository.insertNotes(patientModel)
                }.onSuccess {
                    _addPatientState.send(AddPatientState.AddPatientSuccess("Patient Added"))
                }.onFailure {
                    _addPatientState.send(AddPatientState.Error("Patient adding failed"))
                }
            }
        }
    }

    fun updatePatient(patientModel: PatientModel) = viewModelScope.launch(Dispatchers.IO) {
        when {
            patientModel.patientName.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please enter the name"))
            }

            patientModel.age.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please enter the age"))
            }

            patientModel.gender.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please select the gender"))
            }

            patientModel.toothNumber == 0 -> {
                _addPatientState.send(AddPatientState.Error("Please enter the tooth no"))
            }

            patientModel.toothCode.isNullOrEmpty() -> {
                _addPatientState.send(AddPatientState.Error("Please upload the tooth code"))
            }

            else -> {
                kotlin.runCatching {
                    addPatientRepository.updatePatient(patientModel)
                }.onSuccess {
                    _addPatientState.send(AddPatientState.UpdatePatientSuccess("Patient updated"))
                }.onFailure {
                    _addPatientState.send(AddPatientState.Error("Patient adding failed"))
                }
            }
        }
    }
}