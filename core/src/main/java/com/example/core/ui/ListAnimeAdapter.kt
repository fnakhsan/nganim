package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.databinding.ItemRowAnimeBinding
import com.example.core.domain.model.AnimeModel


class ListAnimeAdapter : RecyclerView.Adapter<ListAnimeAdapter.ListViewHolder>() {
    private lateinit var binding: ItemRowAnimeBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: AnimeModel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemRowAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ListViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AnimeModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivAnime)
                tvAnime.text = data.title
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<AnimeModel>() {
        override fun areItemsTheSame(oldItem: AnimeModel, newItem: AnimeModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimeModel, newItem: AnimeModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}