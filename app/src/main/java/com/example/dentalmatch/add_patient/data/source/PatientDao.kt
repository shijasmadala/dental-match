package com.example.dentalmatch.add_patient.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dentalmatch.add_patient.data.entity.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPatient(patientEntity: PatientEntity)

    @Query("SELECT * FROM patient")
    fun showPatients(): Flow<List<PatientEntity>>

    @Delete
    fun deletePatient(patientEntity: PatientEntity)

    @Update
    fun updatePatient(patientEntity: PatientEntity)
}