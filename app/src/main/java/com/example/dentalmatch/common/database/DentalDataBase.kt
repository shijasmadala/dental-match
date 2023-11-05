package com.example.dentalmatch.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dentalmatch.add_patient.data.entity.PatientEntity
import com.example.dentalmatch.add_patient.data.source.PatientDao
import com.example.dentalmatch.upload_image.data.entity.ColorCodeEntity
import com.example.dentalmatch.upload_image.data.source.ColorCodeDao

@Database(entities = [PatientEntity::class,ColorCodeEntity::class], version = 1)
abstract class DentalDataBase:RoomDatabase() {
    abstract val patientDao : PatientDao
    abstract val colorCodeDao : ColorCodeDao
}