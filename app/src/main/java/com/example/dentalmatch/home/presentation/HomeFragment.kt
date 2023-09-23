package com.example.dentalmatch.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dentalmatch.R
import com.example.dentalmatch.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private val patientListAdapter by lazy { PatientListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setListeners()
        observeShowPatient()
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

    private fun setListeners() {
        binding.apply {
            addPatient.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddPatientFragment())
            }
        }
    }

    private fun setupRecyclerView(){
        binding.apply {
            recyclerView.adapter =patientListAdapter
        }
    }
}