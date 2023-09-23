package com.example.dentalmatch.add_patient.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val patientName : String?,
    val age : String?,
    val gender : String?,
    val toothCode : String?
)
