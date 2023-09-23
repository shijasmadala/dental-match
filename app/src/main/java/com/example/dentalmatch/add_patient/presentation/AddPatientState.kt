package com.example.dentalmatch.add_patient.presentation

sealed class AddPatientState {
    data class AddPatientSuccess(
        val message: String,

        ) : AddPatientState()

    data class Error(val message: String) : AddPatientState()
    data object Loading : AddPatientState()
    data object Empty : AddPatientState()
}
