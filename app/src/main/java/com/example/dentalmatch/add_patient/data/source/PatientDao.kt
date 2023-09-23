package com.example.dentalmatch.add_patient.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dentalmatch.add_patient.data.entity.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addPatient(patientEntity: PatientEntity)

     @Query("SELECT * FROM patient")
     fun showPatients() : Flow<List<PatientEntity>>
}