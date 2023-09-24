package com.example.dentalmatch.add_patient.data.repository

import com.example.dentalmatch.add_patient.data.mapper.toPatientEntity
import com.example.dentalmatch.add_patient.data.source.PatientDao
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.add_patient.domain.repository.AddPatientRepository
import javax.inject.Inject

class AddPatientRepositoryImpl @Inject constructor(private val patientDao: PatientDao) :
    AddPatientRepository {
    override suspend fun insertNotes(patientModel: PatientModel) {
        kotlin.runCatching {
            patientDao.addPatient(patientModel.toPatientEntity())
        }.onSuccess {

        }.onFailure {

        }
    }

    override suspend fun updatePatient(patientModel: PatientModel) {
        kotlin.runCatching {
            patientDao.updatePatient(patientModel.toPatientEntity())
        }.onSuccess {

        }.onFailure {

        }
    }
}