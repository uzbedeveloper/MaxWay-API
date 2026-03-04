package uz.group1.maxwayapp.presentation.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.databinding.ItemCartChequeBinding

class ChequeAdapter : ListAdapter<ProductUIData, ChequeAdapter.ChequeViewHolder>(CartAdapter.CartDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChequeViewHolder {
        val binding = ItemCartChequeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChequeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChequeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            textName.text = item.name
            textDetails.text = "${item.count} x ${String.format("%,d", item.cost).replace(",", " ")}"
            textPrice.text = String.format("%,d", item.cost * item.count).replace(",", " ")
        }
    }

    class ChequeViewHolder(val binding: ItemCartChequeBinding) : RecyclerView.ViewHolder(binding.root)
}