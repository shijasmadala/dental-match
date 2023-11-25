package com.example.dentalmatch.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dentalmatch.R
import com.example.dentalmatch.databinding.FragmentLandingBinding

class LandingFragment : Fragment(R.layout.fragment_landing) {

    private lateinit var binding: FragmentLandingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLandingBinding.bind(view)

        setListeners()
    }

    private fun setListeners() = binding.apply {
        cardPatientData.setOnClickListener {
            findNavController().navigate(LandingFragmentDirections.actionLandingFragmentToHomeFragment())
        }

        cardTeethShades.setOnClickListener {
            findNavController().navigate(LandingFragmentDirections.actionLandingFragmentToUploadImageFragment())
        }
    }
}