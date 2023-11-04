package com.example.dentalmatch.image_handling.presentation

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dentalmatch.R
import com.example.dentalmatch.common.custom_view.ColorPickerFlag
import com.example.dentalmatch.common.util.Constants.CONST_SELECTED_COLOR
import com.example.dentalmatch.databinding.FragmentColorPickerBinding
import com.skydoves.colorpickerview.listeners.ColorListener

class ColorPickerFragment : Fragment(R.layout.fragment_color_picker) {

    private lateinit var binding: FragmentColorPickerBinding
    private val args: ColorPickerFragmentArgs by navArgs()
    private var selectedColor: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorPickerBinding.bind(view)

        init()
        setListeners()
    }

    private fun init() {
        binding.colorPickerView.flagView = ColorPickerFlag(
            requireContext(),
            R.layout.view_color_selector
        )

        val capturedBitmap = args.croppedBitmap
        val drawable = BitmapDrawable(resources, capturedBitmap)
        binding.colorPickerView.setPaletteDrawable(drawable)
    }

    private fun setListeners() = binding.apply {
        colorPickerView.setColorListener(ColorListener { color, _ ->
            selectedColor = color
            viewSelectedColor.setBackgroundColor(color)
        })

        btnContinue.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                CONST_SELECTED_COLOR,
                selectedColor
            )
            findNavController().popBackStack()
        }
    }

}
