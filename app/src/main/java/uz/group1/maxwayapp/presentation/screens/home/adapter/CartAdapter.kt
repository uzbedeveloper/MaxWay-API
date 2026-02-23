package uz.group1.maxwayapp.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.databinding.ItemCartProductBinding
import uz.group1.maxwayapp.utils.loadImage

class CartAdapter(
    private val onCountChanged: (ProductUIData, Int) -> Unit
) : ListAdapter<ProductUIData, CartAdapter.CartViewHolder>(CartDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains("COUNT_CHANGED")) {
            holder.updateCountOnly(getItem(position))
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    inner class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnPlus.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val item = getItem(pos)
                    onCountChanged(item, item.count + 1)
                }
            }

            binding.btnMinus.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val item = getItem(pos)
                    if (item.count > 0) {
                        onCountChanged(item, item.count - 1)
                    }
                }
            }
        }

        fun bind(product: ProductUIData) {
            binding.textName.text = product.name
            binding.textPrice.text = String.format("%,d сум", product.cost * product.count)
            binding.textCount.text = product.count.toString()
            binding.img.loadImage(product.image) {

            }
        }

        fun updateCountOnly(product: ProductUIData) {
            binding.textCount.text = product.count.toString()
            binding.textPrice.text = String.format("%,d сум", product.cost * product.count)
        }
    }

    object CartDiffCallback : DiffUtil.ItemCallback<ProductUIData>() {
        override fun areItemsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean =
            oldItem == newItem

        override fun getChangePayload(oldItem: ProductUIData, newItem: ProductUIData): Any? {
            return if (oldItem.count != newItem.count) "COUNT_CHANGED" else null
        }
    }
}