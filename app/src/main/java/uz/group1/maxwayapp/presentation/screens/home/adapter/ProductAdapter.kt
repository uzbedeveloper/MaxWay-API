package uz.group1.maxwayapp.presentation.screens.main.banner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.databinding.ItemProductBinding
import uz.group1.maxwayapp.utils.loadImage

@Deprecated("Rv Multi modal ni ishlatamaiz")
class ProductAdapter : ListAdapter<ProductUIData, ProductAdapter.ProductViewHolder>(DiffCallback) {

    private var onCountChanged: ((ProductUIData, Int) -> Unit)? = null
    fun setOnCountChangeListener(l: (ProductUIData, Int)-> Unit){
        onCountChanged = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pr: ProductUIData) {
            binding.textName.text = pr.name
            binding.tvPrice.text = "${pr.cost} сум"
            binding.imgProduct.loadImage(pr.image) {}

            val isAddedToCart = pr.count > 0

            if (isAddedToCart) {
                binding.textCost.visibility = View.GONE
                binding.textCount.visibility = View.VISIBLE
                binding.btnMinus.visibility = View.VISIBLE
                binding.btnPlus.visibility = View.VISIBLE
                binding.textCount.text = pr.count.toString()
            } else {
                binding.textCost.visibility = View.VISIBLE
                binding.textCount.visibility = View.GONE
                binding.btnMinus.visibility = View.GONE
                binding.btnPlus.visibility = View.GONE
            }

            binding.cardCost.setOnClickListener {
                if (pr.count == 0) {
                    onCountChanged?.invoke(pr, 1)
                }
            }

            binding.btnPlus.setOnClickListener {
                onCountChanged?.invoke(pr, pr.count + 1)
            }

            binding.btnMinus.setOnClickListener {
                if (pr.count > 0) {
                    onCountChanged?.invoke(pr, pr.count - 1)
                }
            }
        }
    }

    companion object{
        val DiffCallback = object : DiffUtil.ItemCallback<ProductUIData>(){
            override fun areItemsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean {
                return oldItem == newItem
            }

        }
    }
}