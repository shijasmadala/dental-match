package com.example.dentalmatch.home.data.repository

import com.example.dentalmatch.add_patient.data.mapper.toPatientEntity
import com.example.dentalmatch.add_patient.data.mapper.toPatientModel
import com.example.dentalmatch.add_patient.data.source.PatientDao
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val patientDao: PatientDao) : HomeRepository {
    override fun showPatients(): Flow<List<PatientModel>> {
        return patientDao.showPatients().map {
            it.map {
                it.toPatientModel()
            }
        }
    }

    override suspend fun deletePatient(patientModel: PatientModel) {
        kotlin.runCatching {
            patientDao.deletePatient(patientModel.toPatientEntity())
        }
    }
}