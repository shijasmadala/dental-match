package com.example.dentalmatch.add_patient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dentalmatch.R
import com.example.dentalmatch.databinding.FragmentAddPatientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPatientFragment : Fragment(R.layout.fragment_add_patient) {
    private lateinit var binding: FragmentAddPatientBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPatientBinding.bind(view)
    }

}