package com.example.dentalmatch.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalmatch.add_patient.domain.model.PatientModel
import com.example.dentalmatch.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    private val _homeState = Channel<HomeState>()
    val homeState = _homeState.receiveAsFlow()
    val showPatients = homeRepository.showPatients()

    fun deletePatient(patientModel: PatientModel) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            homeRepository.deletePatient(patientModel)
        }.onSuccess {
            _homeState.send(HomeState.DeletePatient("Patient Deleted"))
        }.onFailure {

        }
    }
}