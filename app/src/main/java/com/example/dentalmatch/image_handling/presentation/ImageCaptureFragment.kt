package com.example.dentalmatch.image_handling.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.example.dentalmatch.R
import com.example.dentalmatch.common.util.Constants.CONST_CAPTURED_IMAGE
import com.example.dentalmatch.common.util.Constants.TEST_TAG
import com.example.dentalmatch.databinding.FragmentImageCaptureBinding
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class ImageCaptureFragment : Fragment(R.layout.fragment_image_capture) {

    private lateinit var binding: FragmentImageCaptureBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService
    private var capturedImageFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageCaptureBinding.bind(view)

        init()
        setListeners()
    }

    private val cameraProviderResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                // cut and paste the previous startCamera() call here.
                startCamera()
            } else {
                Snackbar.make(
                    binding.root,
                    "The camera permission is required",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        }

    private fun init() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        // Check for camera permissions, and start camera if permissions are granted
        cameraProviderResult.launch(android.Manifest.permission.CAMERA)
    }

    private fun setListeners() = binding.apply {
        btnCapture.setOnClickListener {
            takePhoto()
            animateFlash()
        }

        btnCancel.setOnClickListener {
            groupPreview.isVisible = false
            groupCapture.isVisible = true
        }

        btnContinue.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                CONST_CAPTURED_IMAGE,
                capturedImageFile
            )
            findNavController().popBackStack()
        }
    }

    private fun startCamera() {
        // listening for data from the camera
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // connecting a preview use case to the preview in the xml file.
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.preview.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
            try {
                // clear all the previous use cases first.
                cameraProvider.unbindAll()
                // binding the lifecycle of the camera to the lifecycle of the application.
                cameraProvider.bindToLifecycle(
                    requireActivity(),
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.d(TEST_TAG, "Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        imageCapture?.let {
            //Create a storage location whose fileName is timestamped in milliseconds.
            val fileName = "JPEG_${System.currentTimeMillis()}.jpeg"
            val file = File(requireContext().externalMediaDirs[0], fileName)

            // Save the image in the above file
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

            /* pass in the details of where and how the image is taken.(arguments 1 and 2 of takePicture)
            pass in the details of what to do after an image is taken.(argument 3 of takePicture) */

            it.takePicture(
                outputFileOptions,
                imgCaptureExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Log.d(TEST_TAG, "The image has been saved in ${file.toUri()}")
                        lifecycleScope.launch(Dispatchers.Main) {
                            setCapturedImage(file)
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(
                            binding.root.context,
                            "Error capturing photo",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d(TEST_TAG, "Error capturing photo:$exception")
                    }

                })
        }
    }

    private fun setCapturedImage(file: File) = binding.apply {
        capturedImageFile = file
        groupPreview.isVisible = true
        imgCapturedImage.load(capturedImageFile)
    }

    private fun animateFlash() {
        binding.root.postDelayed({
            binding.root.foreground = ColorDrawable(Color.WHITE)
            binding.root.postDelayed({
                binding.root.foreground = null
            }, 50)
        }, 100)
    }

}
