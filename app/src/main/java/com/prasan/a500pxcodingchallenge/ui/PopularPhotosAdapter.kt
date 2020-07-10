package com.prasan.a500pxcodingchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prasan.a500pxcodingchallenge.databinding.PopularPhotosItemBinding
import com.prasan.a500pxcodingchallenge.loadUrl
import com.prasan.a500pxcodingchallenge.model.datamodel.Photo

class PopularPhotosAdapter(private val itemList: List<Photo>) :
    RecyclerView.Adapter<PopularPhotosAdapter.PopularPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPhotosViewHolder =
        PopularPhotosViewHolder(PopularPhotosItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: PopularPhotosViewHolder, position: Int) =
        holder.bind(itemList[position])

    override fun getItemCount() = itemList.size

    class PopularPhotosViewHolder(private val binding: PopularPhotosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.popularPhoto.loadUrl(photo.imageUrl[0])
        }
    }
}