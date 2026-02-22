package uz.group1.maxwayapp.presentation.screens.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.databinding.ItemCategoryBinding

class CategoryAdapter: ListAdapter<CategoryChipUI, CategoryAdapter.CategoryViewHolder>(diffUtil) {

    private var onItemClick: ((CategoryChipUI) -> Unit)? = null
    fun setOnItemClickListener(l: (CategoryChipUI)-> Unit){
        onItemClick = l
    }
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<CategoryChipUI>(){
            override fun areItemsTheSame(oldItem: CategoryChipUI, newItem: CategoryChipUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryChipUI, newItem: CategoryChipUI): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(category: CategoryChipUI){
            binding.apply {
                textCategoryName.text = category.name

                if (category.isSelected) {
                    val color = ContextCompat.getColor(root.context, R.color.selected_category)
                    root.setCardBackgroundColor(color)
                } else {
                    val color = ContextCompat.getColor(root.context, R.color.white)
                    root.setCardBackgroundColor(color)
                }

                root.setOnClickListener {
                    onItemClick?.invoke(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}