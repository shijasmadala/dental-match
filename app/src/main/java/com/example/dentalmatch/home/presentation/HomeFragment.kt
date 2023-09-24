package com.example.dentalmatch.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dentalmatch.R
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), PatientListAdapter.OnPatientClick {
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private val patientListAdapter by lazy { PatientListAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setListeners()
        observeShowPatient()
        observeHomeState()
        setupRecyclerView()
    }

    private fun observeShowPatient() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showPatients.collectLatest {
                    patientListAdapter.submitList(it)
                }
            }
        }
    }

    private fun observeHomeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collectLatest {
                    when (it) {
                        is HomeState.DeletePatient -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                        is HomeState.Empty -> {}
                        is HomeState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is HomeState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            addPatient.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAddPatientFragment(
                        null
                    )
                )
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerView.adapter = patientListAdapter
        }
    }

    override fun updatePatient(patientModel: PatientModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToAddPatientFragment(
                patientModel
            )
        )
    }

    override fun deletePatient(patientModel: PatientModel) {
        viewModel.deletePatient(patientModel)
    }
}