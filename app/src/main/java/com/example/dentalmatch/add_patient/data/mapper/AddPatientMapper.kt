package com.example.dentalmatch.add_patient.data.mapper

import com.example.dentalmatch.add_patient.data.entity.PatientEntity
import com.example.dentalmatch.add_patient.domain.model.PatientModel

fun PatientEntity.toPatientModel() :PatientModel{
    return PatientModel(
        id = id,patientName = patientName, age = age, gender = gender, toothCode = toothCode
    )
}

fun PatientModel.toPatientEntity() :PatientEntity{
    return PatientEntity(
        id = id,patientName = patientName, age = age, gender = gender, toothCode = toothCode
    )
}