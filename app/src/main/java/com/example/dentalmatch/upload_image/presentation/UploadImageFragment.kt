package com.example.dentalmatch.upload_image.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dentalmatch.R
import com.example.dentalmatch.common.util.Constants
import com.example.dentalmatch.databinding.FragmentUploadImageBinding
import com.example.dentalmatch.upload_image.domain.ColorCodeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UploadImageFragment : Fragment(R.layout.fragment_upload_image) {
    private lateinit var binding: FragmentUploadImageBinding
    private val viewModel by viewModels<UploadImageVieModel>()
    private var selectedColor : Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadImageBinding.bind(view)
        observeUploadImageState()
        setListeners()
        observeCapturedImage()
    }

    private fun observeUploadImageState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addImageState.collectLatest {
                    when (it) {

                        is UploadImageState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                        is UploadImageState.Loading -> {}

                        is UploadImageState.UploadImageSuccess -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setListeners(){
        binding.apply {
            submit.setOnClickListener {
                selectedColor?.let { color ->
                    val colorCodeModel = ColorCodeModel(
                        teethCode = imageCodeEdt.text.toString(),
                        color = color,
                        id = 0
                    )
                    viewModel.addColor(colorCodeModel)
                }
            }
            imageColorEdt.setOnClickListener {
                findNavController().navigate(
                    UploadImageFragmentDirections.actionUploadImageFragmentToImageCaptureFragment(
                        from = Constants.FROM_UPLOAD
                    )
                )
            }
        }
    }

    private fun observeCapturedImage() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(Constants.CONST_SELECTED_COLOR)?.observe(
            viewLifecycleOwner) { color ->
            if (color != null) {
                Log.d(Constants.TEST_TAG, "Returned color in int: $selectedColor")
               selectedColor = color
                binding.submit.isEnabled = true
                binding.imageColorLay.setCardBackgroundColor(color)
            }
        }
    }

}