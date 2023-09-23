package com.example.dentalmatch.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dentalmatch.add_patient.data.entity.PatientEntity
import com.example.dentalmatch.add_patient.data.source.PatientDao

@Database(entities = [PatientEntity::class], version = 1)
abstract class DentalDataBase:RoomDatabase() {
    abstract val patientDao : PatientDao
}