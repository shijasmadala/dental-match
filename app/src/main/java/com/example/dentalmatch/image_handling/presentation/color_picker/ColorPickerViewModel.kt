package com.example.dentalmatch.image_handling.presentation.color_picker

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ColorPickerViewModel : ViewModel() {

    private val _selectedColorsList = MutableStateFlow<List<Int>>(mutableListOf())
    val selectedColorsList = _selectedColorsList.asStateFlow()

    private val _averageColor = MutableStateFlow<Int?>(null)
    val averageColor = _averageColor.asStateFlow()

    fun isColorAdded(): Boolean = _selectedColorsList.value.isNotEmpty()

    fun addColor(color: Int) = viewModelScope.launch {
        val colorsList = _selectedColorsList.value.toMutableList()
        colorsList.add(color)
        calculateAverageColor(colorsList)
        _selectedColorsList.emit(colorsList)
    }

    private fun calculateAverageColor(colorsList: List<Int>) = viewModelScope.launch {
        var totalR = 0
        var totalG = 0
        var totalB = 0
        val totalSize = colorsList.size

        colorsList.map { color ->
            totalR += Color.red(color)
            totalG += Color.green(color)
            totalB += Color.blue(color)
        }

        // Find the average
        val averageR = totalR / totalSize
        val averageG = totalG / totalSize
        val averageB = totalB / totalSize
        val averageColor = Color.rgb(averageR, averageG, averageB)
        _averageColor.emit(averageColor)
    }
}
