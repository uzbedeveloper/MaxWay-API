package uz.group1.maxwayapp.presentation.screens.main.banner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.databinding.ItemProductBinding

class ProductAdapter : ListAdapter<ProductUIData, ProductAdapter.ProductViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pr: ProductUIData){
            binding.textName.text = pr.name
            binding.textCost.text = pr.cost.toString()


            Glide.with(binding.imgProduct.context)
                .load(pr.image)
                .centerCrop()
                .into(binding.imgProduct)


            binding.cardCost.setOnClickListener {
                binding.textCost.visibility = View.GONE
                binding.textCount.visibility = View.VISIBLE
                binding.btnMinus.visibility = View.VISIBLE
                binding.btnPlus.visibility = View.VISIBLE

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