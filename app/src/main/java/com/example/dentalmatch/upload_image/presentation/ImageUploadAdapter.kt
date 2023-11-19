package com.example.dentalmatch.upload_image.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dentalmatch.databinding.ItemImageUploadBinding
import com.example.dentalmatch.upload_image.domain.ColorCodeModel

class ImageUploadAdapter (private val listener: ImageUploadClick): ListAdapter<ColorCodeModel, RecyclerView.ViewHolder>(ImageUploadDiff) {

    interface ImageUploadClick{
        fun deleteUploadedColor(colorCodeModel: ColorCodeModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemImageUploadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageUploadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageUploadViewHolder).bind(getItem(position))
    }

    inner class ImageUploadViewHolder(private val binding: ItemImageUploadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(colorCodeModel: ColorCodeModel) {
            binding.apply {
                toothCode.text = colorCodeModel.teethCode
                colorCard.setCardBackgroundColor(colorCodeModel.color)

                deletePatient.setOnClickListener {
                    listener.deleteUploadedColor(colorCodeModel)
                }
            }
        }
    }
}

object ImageUploadDiff : DiffUtil.ItemCallback<ColorCodeModel>() {
    override fun areItemsTheSame(oldItem: ColorCodeModel, newItem: ColorCodeModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ColorCodeModel, newItem: ColorCodeModel): Boolean {
        return oldItem == newItem
    }

}