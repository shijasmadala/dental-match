package com.example.dentalmatch.home.presentation

import com.example.dentalmatch.add_patient.presentation.AddPatientState

sealed class HomeState {
    data class DeletePatient(val message: String) : HomeState()
    data class Error(val message: String) : HomeState()
    data object Loading : HomeState()
    data object Empty : HomeState()
}