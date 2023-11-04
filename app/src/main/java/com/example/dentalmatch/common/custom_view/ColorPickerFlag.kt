package com.example.dentalmatch.common.custom_view

import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.dentalmatch.R
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.flag.FlagView

class ColorPickerFlag(context: Context, layout: Int) : FlagView(context, layout) {

    private var textView: TextView = findViewById(R.id.flag_color_code)
    private var view: View = findViewById(R.id.flag_color_layout)

    override fun onRefresh(colorEnvelope: ColorEnvelope?) {
        colorEnvelope?.color?.let { color ->
            textView.text = "#" + String.format("%06X", 0xFFFFFF and color)
            view.setBackgroundColor(color)
        }
    }

}