package com.example.dentalmatch.add_patient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientModel(
    val id : Int,
    val patientName : String?,
    val age : String?,
    val gender : String?,
    val toothCode : String?,
    val toothNumber : Int?
) : Parcelable {
    override fun toString(): String {
        return " Patient Name:$patientName\n Age:$age\n Gender:$gender\n Tooth Code:$toothCode\n Tooth Number:$toothNumber"
    }
}
