package com.example.dentalmatch.add_patient.domain.repository

import com.example.dentalmatch.add_patient.domain.model.PatientModel

interface AddPatientRepository {
    suspend fun insertNotes(patientModel: PatientModel)
}