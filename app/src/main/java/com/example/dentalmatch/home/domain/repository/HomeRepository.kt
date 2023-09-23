package com.example.dentalmatch.home.domain.repository

import com.example.dentalmatch.add_patient.domain.model.PatientModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun showPatients() : Flow<List<PatientModel>>
}