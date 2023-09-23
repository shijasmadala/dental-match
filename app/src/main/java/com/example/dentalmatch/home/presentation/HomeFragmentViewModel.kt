package com.example.dentalmatch.home.presentation

import androidx.lifecycle.ViewModel
import com.example.dentalmatch.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    val showPatients = homeRepository.showPatients()
}