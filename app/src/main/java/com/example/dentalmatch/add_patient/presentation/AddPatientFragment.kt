package com.example.dentalmatch.add_patient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dentalmatch.R
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.databinding.FragmentAddPatientBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPatientFragment : Fragment(R.layout.fragment_add_patient) {
    private lateinit var binding: FragmentAddPatientBinding
    private val viewModel by viewModels<AddPatientViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPatientBinding.bind(view)
        observePatientAdd()
        setListners()
        setGenderAdapter()
    }

    private fun setListners() {
        binding.apply {
            submitPatient.setOnClickListener {
                val patientModel = PatientModel(
                    0, patientName = patientNameEdt.text.toString(),
                    age = patientAgeEdt.text.toString(), gender = patientGenderEdt.text.toString(),
                    toothCode = uploadToothImgEdt.text.toString()
                )
                viewModel.addPatient(patientModel)
            }
        }
    }

    private fun setGenderAdapter() {
        val genderList = arrayListOf<String>()
        genderList.add("Male")
        genderList.add("female")
        if (genderList != null) {
            val adapter = context?.let { it1 ->
                ArrayAdapter(
                    it1,
                    android.R.layout.simple_spinner_dropdown_item,
                    genderList// Display names
                )
            }
            binding.patientGenderEdt.setAdapter(adapter)
        }
    }

    private fun observePatientAdd() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addPatientState.collectLatest {
                    when (it) {
                        is AddPatientState.Loading -> {}

                        is AddPatientState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                        is AddPatientState.AddPatientSuccess -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}