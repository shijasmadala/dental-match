package com.example.dentalmatch.image_handling.presentation.color_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dentalmatch.databinding.ItemMultipleColorsBinding

class MultiColorAdapter : ListAdapter<Int, RecyclerView.ViewHolder>(MultiColorDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemMultipleColorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MultiColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MultiColorViewHolder).bind(getItem(position))
    }

    inner class MultiColorViewHolder(private val binding: ItemMultipleColorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Int) = binding.apply {
            colorView.setBackgroundColor(color)

            // TODO: Handle the click if needed to delete
        }
    }
}

object MultiColorDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}