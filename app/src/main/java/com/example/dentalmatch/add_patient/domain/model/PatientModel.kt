package com.example.dentalmatch.add_patient.domain.model

data class PatientModel(
    val id : Int,
    val patientName : String?,
    val age : String?,
    val gender : String?,
    val toothCode : String?
)
