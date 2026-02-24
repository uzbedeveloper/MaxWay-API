package uz.group1.maxwayapp.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.databinding.ItemCategoryHeaderBinding
import uz.group1.maxwayapp.databinding.ItemProductBinding
import uz.group1.maxwayapp.domain.models.HomeItem
import uz.group1.maxwayapp.utils.loadImage

class HomeMainAdapter(
    private val onCountChanged: (ProductUIData, Int) -> Unit,
    private val onItemClick:(ProductUIData,Int) -> Unit
) : ListAdapter<HomeItem, RecyclerView.ViewHolder>(HomeItemDiffCallback) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_PRODUCT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeItem.CategoryHeader -> TYPE_HEADER
            is HomeItem.ProductItem -> TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            HeaderViewHolder(ItemCategoryHeaderBinding.inflate(inflater, parent, false))
        } else {
            ProductViewHolder(ItemProductBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeItem.CategoryHeader -> (holder as HeaderViewHolder).bind(item.name)
            is HomeItem.ProductItem -> (holder as ProductViewHolder).bind(item.product)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val item = getItem(position)
            if (payloads.contains("COUNT_CHANGED") && item is HomeItem.ProductItem && holder is ProductViewHolder) {
                holder.updateCountOnly(item.product)
            }
        }
    }


    inner class HeaderViewHolder(private val binding: ItemCategoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.categoryName.text = name
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pr: ProductUIData) {
            binding.textName.text = pr.name
            binding.tvPrice.text = "${pr.cost} сум"
            binding.imgProduct.loadImage(pr.image) {}

            setupClickListeners()
            updateUI(pr)

            binding.root.setOnClickListener {
                onItemClick(pr,absoluteAdapterPosition)
            }
        }

        private fun setupClickListeners() {
            binding.btnPlus.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = getItem(position) as? HomeItem.ProductItem
                    currentItem?.let {
                        onCountChanged(it.product, it.product.count + 1)
                    }
                }
            }

            binding.btnMinus.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = getItem(position) as? HomeItem.ProductItem
                    currentItem?.let {
                        if (it.product.count > 0) {
                            onCountChanged(it.product, it.product.count - 1)
                        }
                    }
                }
            }
            binding.cardCost.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = getItem(position) as? HomeItem.ProductItem
                    currentItem?.let {
                        if (it.product.count == 0) onCountChanged(it.product, 1)
                    }
                }
            }
        }

        fun updateCountOnly(pr: ProductUIData) {
            updateUI(pr)
        }
        private fun updateUI(pr: ProductUIData) {
            val isInCart = pr.count > 0
            binding.textCost.isVisible = !isInCart
            binding.textCount.isVisible = isInCart
            binding.btnMinus.isVisible = isInCart
            binding.btnPlus.isVisible = isInCart
            binding.textCount.text = pr.count.toString()
        }
    }

    object HomeItemDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return if (oldItem is HomeItem.ProductItem && newItem is HomeItem.ProductItem) {
                oldItem.product.id == newItem.product.id
            } else if (oldItem is HomeItem.CategoryHeader && newItem is HomeItem.CategoryHeader) {
                oldItem.name == newItem.name
            } else false
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: HomeItem, newItem: HomeItem): Any? {
            if (oldItem is HomeItem.ProductItem && newItem is HomeItem.ProductItem) {
                if (oldItem.product.count != newItem.product.count) {
                    return "COUNT_CHANGED"
                }
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
}

