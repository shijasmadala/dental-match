package com.example.dentalmatch.image_handling.presentation.color_matching

import android.animation.Animator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.dentalmatch.databinding.DialogColorMatcherBinding

class ColorMatcherDialog(private val selectedColor: Int) : DialogFragment() {

    private lateinit var binding: DialogColorMatcherBinding
    private var matchingColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogColorMatcherBinding.inflate(inflater, container, false)

        init()
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
        binding.tvLoadingText.text = "Selected color: $selectedColor"
    }

    private fun setListeners() = binding.apply {
        lottieLoader.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                groupLoader.isVisible = false
                groupData.isVisible = true
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
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