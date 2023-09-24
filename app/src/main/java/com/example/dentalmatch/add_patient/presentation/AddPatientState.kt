package com.example.dentalmatch.add_patient.presentation

import com.example.dentalmatch.add_patient.domain.model.PatientModel

sealed class AddPatientState {
    data class AddPatientSuccess(
        val message: String,

        ) : AddPatientState()

    data class UpdatePatientSuccess(
        val message: String,

        ) : AddPatientState()

    data class UpdatePatientData(val patientModel: PatientModel?) : AddPatientState()

    data class Error(val message: String) : AddPatientState()
    data object Loading : AddPatientState()
    data object Empty : AddPatientState()
}
