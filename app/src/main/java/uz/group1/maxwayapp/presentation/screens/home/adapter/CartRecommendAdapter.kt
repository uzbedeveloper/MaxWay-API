package uz.group1.maxwayapp.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.databinding.ItemProductBinding
import uz.group1.maxwayapp.databinding.ItemProductHorizontalBinding
import uz.group1.maxwayapp.utils.loadImage

class CartRecommendAdapter(
    private val onCountChanged: (ProductUIData, Int) -> Unit
) : ListAdapter<ProductUIData, CartRecommendAdapter.CartViewHolder>(CartDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemProductHorizontalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(private val binding: ItemProductHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAdd.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCountChanged(getItem(position), 1)
                }
            }
        }

        fun bind(product: ProductUIData) {
            binding.textName.text = product.name
            binding.imgProduct.loadImage(product.image) {}
            binding.textCount.text = product.count.toString()
            binding.textCost.text = String.format("%,d сум", product.cost)
        }
    }

    object CartDiffCallback : DiffUtil.ItemCallback<ProductUIData>() {
        override fun areItemsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean =
            oldItem == newItem
    }
}