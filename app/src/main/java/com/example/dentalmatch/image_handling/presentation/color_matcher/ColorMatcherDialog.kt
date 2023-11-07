package com.example.dentalmatch.image_handling.presentation.color_matcher

import android.animation.Animator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dentalmatch.databinding.DialogColorMatcherBinding
import com.example.dentalmatch.upload_image.domain.ColorCodeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@AndroidEntryPoint
class ColorMatcherDialog(
    private val selectedColor: Int,
    private val listener: MatchListener
) : DialogFragment() {

    private lateinit var binding: DialogColorMatcherBinding
    private val viewModel by viewModels<ColorMatcherViewModel>()
    private var showSuccessGroup: Boolean? = null
    private var matchingColorData: ColorCodeModel? = null

    interface MatchListener {
        fun onColorMatched(matchedCode: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogColorMatcherBinding.inflate(inflater, container, false)

        init()
        observeState()
        setListeners()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customizeDialog(dialog)
        return dialog
    }

    private fun init() {
        binding.cardSelectedColor.setCardBackgroundColor(selectedColor)
    }

    private fun setListeners() = binding.apply {
        lottieLoader.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                showSuccessGroup?.let { flag ->
                    groupLoader.isVisible = false
                    if (flag)
                        groupData.isVisible = true
                    else {
                        groupError.isVisible = true
                        lottieError.playAnimation()
                    }
                }
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        binding.btnContinue.setOnClickListener {
            matchingColorData?.let { colorData ->
                listener.onColorMatched(colorData.teethCode)
                dismiss()
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    when (it) {
                        is ColorMatcherState.SuccessColorsList -> handleColorsListSuccess(it.colorsList)
                        is ColorMatcherState.Error -> handleColorsListError()
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun handleColorsListSuccess(colorsList: List<ColorCodeModel>) {
        showSuccessGroup = true
        matchingColorData = findMatchingColor(colorsList)

        matchingColorData?.let { matchedColorData ->
            binding.apply {
                tvMatchedCode.text = matchedColorData.teethCode
                cardMatchedColor.setCardBackgroundColor(matchedColorData.color)
            }
        }
    }

    private fun handleColorsListError() {
        showSuccessGroup = false
    }

    private fun findMatchingColor(colorsList: List<ColorCodeModel>): ColorCodeModel? {
        val selectedR = Color.red(selectedColor)
        val selectedG = Color.green(selectedColor)
        val selectedB = Color.blue(selectedColor)

        var minColorDifference: Double? = null
        var matchingColorData: ColorCodeModel? = null

        colorsList.map { colorData ->
            val diff = sqrt(
                (selectedR - Color.red(colorData.color)).toDouble().pow(2.0)
                        + (selectedG - Color.green(colorData.color)).toDouble().pow(2.0)
                        + (selectedB - Color.blue(colorData.color)).toDouble().pow(2.0)
            )
            val newColorDifference = abs(diff)

            Log.d("TEST111", "${colorData.teethCode}, $newColorDifference")

            if (minColorDifference == null) {
                minColorDifference = newColorDifference
                matchingColorData = colorData
            } else if (newColorDifference < minColorDifference!!) {
                minColorDifference = newColorDifference
                matchingColorData = colorData
            }

            /*if (oldColorDifference != null && newColorDifference < oldColorDifference!!)
                matchingColorData = colorData

            oldColorDifference = newColorDifference*/
        }
        return matchingColorData
    }

    private fun customizeDialog(dialog: Dialog) {
        val lWindowParams = WindowManager.LayoutParams()
        lWindowParams.copyFrom(dialog.window!!.attributes)
        lWindowParams.width =
            WindowManager.LayoutParams.MATCH_PARENT // this is where the magic happens
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.show() // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
        dialog.window!!.attributes = lWindowParams

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // To dim the background of the dialog
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val lp: WindowManager.LayoutParams = dialog.window!!.attributes
        lp.dimAmount = 0.7f // Dim level. 0.0 - no dim, 1.0 - completely opaque
        dialog.window!!.attributes = lp
    }

    companion object {
        const val TAG = "ColorMatcherDialog"
    }
}