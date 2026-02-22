package uz.group1.maxwayapp.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.databinding.ItemStoryCircleBinding
import uz.group1.maxwayapp.utils.loadImage

class StoriesAdapter() : ListAdapter<StoryUIData, StoriesAdapter.StoriesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding = ItemStoryCircleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StoriesViewHolder(private val binding: ItemStoryCircleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StoryUIData) {
            binding.title.text = data.title
            binding.card.visibility = View.INVISIBLE
            binding.loader.visibility = View.VISIBLE
            binding.img.loadImage(data.imageUrl){
                binding.card.visibility = View.VISIBLE
                binding.loader.visibility = View.GONE
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<StoryUIData>() {
            override fun areItemsTheSame(oldItem: StoryUIData, newItem: StoryUIData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryUIData, newItem: StoryUIData): Boolean {
                return oldItem == newItem
            }
        }
    }
}