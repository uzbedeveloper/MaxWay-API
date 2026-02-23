package uz.group1.maxwayapp.presentation.screens.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.ProductSearchUIData
import uz.group1.maxwayapp.databinding.ItemSearchProductBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class SearchAdapter : ListAdapter<ProductSearchUIData, SearchAdapter.SearchViewHolder>(diffUtil) {

    private val formatter = DecimalFormat("#,###").apply {
        decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductSearchUIData>() {
            override fun areItemsTheSame(oldItem: ProductSearchUIData, newItem: ProductSearchUIData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductSearchUIData, newItem: ProductSearchUIData): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class SearchViewHolder(private val binding: ItemSearchProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pr: ProductSearchUIData) {
            binding.apply {
                tvName.text = pr.name
                tvPrice.text = "${formatter.format(pr.cost)} сум"

                Glide.with(root.context)
                    .load(pr.image)
                    .placeholder(R.drawable.img)
                    .centerInside()
                    .centerCrop()
                    .into(imgFood)
            }
        }
    }
}