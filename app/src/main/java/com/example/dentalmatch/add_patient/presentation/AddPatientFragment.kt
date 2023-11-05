package com.example.dentalmatch.add_patient.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dentalmatch.R
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.common.util.Constants.CONST_SELECTED_COLOR
import com.example.dentalmatch.common.util.Constants.FROM_PATIENT
import com.example.dentalmatch.common.util.Constants.TEST_TAG
import com.example.dentalmatch.common.util.toHexColor
import com.example.dentalmatch.databinding.FragmentAddPatientBinding
import com.example.dentalmatch.image_handling.presentation.color_matching.ColorMatcherDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPatientFragment : Fragment(R.layout.fragment_add_patient) {

    private lateinit var binding: FragmentAddPatientBinding
    private val viewModel by viewModels<AddPatientViewModel>()
    private val args: AddPatientFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPatientBinding.bind(view)

        observePatientAdd()
        setListeners()
        setGenderAdapter()
        observeCapturedImage()
    }

    private fun setListeners() {
        binding.apply {
            submitPatient.setOnClickListener {
                if (args.patientData != null) {
                    val patientModel = PatientModel(
                        args.patientData!!.id,
                        patientName = patientNameEdt.text.toString(),
                        age = patientAgeEdt.text.toString(),
                        gender = patientGenderEdt.text.toString(),
                        toothCode = uploadToothImgEdt.text.toString()
                    )
                    viewModel.updatePatient(patientModel)
                } else {
                    val patientModel = PatientModel(
                        0,
                        patientName = patientNameEdt.text.toString(),
                        age = patientAgeEdt.text.toString(),
                        gender = patientGenderEdt.text.toString(),
                        toothCode = uploadToothImgEdt.text.toString()
                    )
                    viewModel.addPatient(patientModel)
                }
            }

            uploadToothImgEdt.setOnClickListener {
                findNavController().navigate(
                    AddPatientFragmentDirections.actionAddPatientFragmentToImageCaptureFragment(
                        FROM_PATIENT
                    )
                )
            }
        }
    }

    private fun setGenderAdapter() {
        val genderList = arrayListOf<String>()
        genderList.add("Male")
        genderList.add("Female")
        genderList.add("Other")
        val adapter = context?.let { it1 ->
            ArrayAdapter(
                it1,
                android.R.layout.simple_spinner_dropdown_item,
                genderList// Display names
            )
        }
        binding.patientGenderEdt.setAdapter(adapter)
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

                        is AddPatientState.UpdatePatientData -> {
                            binding.apply {
                                if (it.patientModel != null) {
                                    patientNameEdt.setText(it.patientModel.patientName.toString())
                                    patientAgeEdt.setText(it.patientModel.age.toString())
                                    patientGenderEdt.setText(
                                        it.patientModel.gender.toString(),
                                        false
                                    )
                                    uploadToothImgEdt.setText(it.patientModel.toothCode.toString())
                                }
                            }
                        }

                        is AddPatientState.UpdatePatientSuccess -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeCapturedImage() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            CONST_SELECTED_COLOR
        )?.observe(
            viewLifecycleOwner
        ) { selectedColor ->
            if (selectedColor != null) {
                Log.d(TEST_TAG, "Returned color in int: $selectedColor")
                binding.uploadToothImgEdt.setText(selectedColor.toHexColor())

                // Show the matcher dialog
                val dialog = ColorMatcherDialog(selectedColor)
                dialog.show(requireActivity().supportFragmentManager, ColorMatcherDialog.TAG)
            }
        }
    }

}
