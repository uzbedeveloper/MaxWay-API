package uz.group1.maxwayapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.databinding.ItemStoryBinding
import uz.group1.maxwayapp.utils.loadImage

class StoryAdapter(private val stories: List<StoryUIData>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(story: StoryUIData){
            binding.storyImage.loadImage(story.imageUrl)
            binding.storyTitle.text = story.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.onBind(story)
    }

    override fun getItemCount(): Int = stories.size
}