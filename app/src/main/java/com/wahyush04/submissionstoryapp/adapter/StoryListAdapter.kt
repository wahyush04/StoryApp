package com.wahyush04.submissionstoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wahyush04.submissionstoryapp.data.ListStory
import com.wahyush04.submissionstoryapp.databinding.ItemStoryBinding

class StoryListAdapter(private var onItemClickCallback: OnItemClickCallback) :
    PagingDataAdapter<ListStory, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStory) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .centerCrop()
                    .into(ivStory)
                tvName.text = data.name
                tvDescription.text = data.description
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ListStory)
    }

}