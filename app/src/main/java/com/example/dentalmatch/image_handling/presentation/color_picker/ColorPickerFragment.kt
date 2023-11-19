package com.example.dentalmatch.image_handling.presentation.color_picker

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dentalmatch.R
import com.example.dentalmatch.common.custom_view.ColorPickerFlag
import com.example.dentalmatch.common.util.Constants.CONST_SELECTED_COLOR
import com.example.dentalmatch.databinding.FragmentColorPickerBinding
import com.skydoves.colorpickerview.listeners.ColorListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ColorPickerFragment : Fragment(R.layout.fragment_color_picker) {

    private lateinit var binding: FragmentColorPickerBinding
    private val viewModel by viewModels<ColorPickerViewModel>()
    private val adapter: MultiColorAdapter by lazy { MultiColorAdapter() }
    private val args: ColorPickerFragmentArgs by navArgs()
    private var averageColor: Int? = null
    private var selectedColor: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorPickerBinding.bind(view)

        init()
        setupRecyclerView()
        setListeners()
        setListObservers()
        setObservers()
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

    private fun setupRecyclerView() = binding.apply {
        recyclerColors.adapter = adapter
    }

    private fun setListeners() = binding.apply {
        colorPickerView.setColorListener(ColorListener { color, _ ->
            selectedColor = color
        })

        btnContinue.setOnClickListener {
            if (viewModel.isColorAdded()) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    CONST_SELECTED_COLOR,
                    averageColor
                )
                findNavController().popBackStack()
            }
            else {
                Toast.makeText(requireContext(), "Add at-least one color", Toast.LENGTH_LONG).show()
            }
        }

        btnAdd.setOnClickListener {
            selectedColor?.let {
                viewModel.addColor(it)
            }
        }
    }

    private fun setListObservers() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.selectedColorsList.collectLatest { colorsList ->
                adapter.submitList(colorsList)
            }
        }
    }

    private fun setObservers() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.averageColor.collectLatest {
                it?.let { averageColor ->
                    this@ColorPickerFragment.averageColor = averageColor
                    binding.viewSelectedColor.setBackgroundColor(averageColor)
                }
            }
        }
    }
}
