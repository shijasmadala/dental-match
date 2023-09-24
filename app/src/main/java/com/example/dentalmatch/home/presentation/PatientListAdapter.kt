package com.example.dentalmatch.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.databinding.ItemPatientBinding

class PatientListAdapter(private val listener: OnPatientClick) : ListAdapter<PatientModel, RecyclerView.ViewHolder>(PatientsDiff) {

    interface OnPatientClick{
        fun updatePatient(patientModel: PatientModel)
        fun deletePatient(patientModel: PatientModel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PatientViewHolder).bind(getItem(position))
    }

    inner class PatientViewHolder(private val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(patientModel: PatientModel) {
            binding.apply {
                patientName.text = patientModel.patientName
                age.text = "Age :" +patientModel.age
                gender.text = patientModel.gender
                toothCode.text = "Tooth Code :" + patientModel.toothCode

                root.setOnClickListener {
                    listener.updatePatient(patientModel)
                }
                deletePatient.setOnClickListener {
                    listener.deletePatient(patientModel)
                }
            }
        }
    }
}

object PatientsDiff : DiffUtil.ItemCallback<PatientModel>() {
    override fun areItemsTheSame(oldItem: PatientModel, newItem: PatientModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PatientModel, newItem: PatientModel): Boolean {
        return oldItem == newItem
    }

}