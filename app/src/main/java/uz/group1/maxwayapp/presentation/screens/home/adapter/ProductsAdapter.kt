package uz.group1.maxwayapp.presentation.screens.main.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.databinding.ItemProductsBinding

class ProductsAdapter : ListAdapter<CategoryUIData, ProductsAdapter.ProductsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductsViewHolder(private val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {

        private val productAdapter = ProductAdapter()

        fun bind(category: CategoryUIData) {
            binding.categoryName.text = category.name
            binding.rvProducts.adapter = productAdapter
            productAdapter.submitList(category.products)
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<CategoryUIData>() {
            override fun areItemsTheSame(oldItem: CategoryUIData, newItem: CategoryUIData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryUIData, newItem: CategoryUIData): Boolean {
                return oldItem == newItem
            }
        }
    }
}