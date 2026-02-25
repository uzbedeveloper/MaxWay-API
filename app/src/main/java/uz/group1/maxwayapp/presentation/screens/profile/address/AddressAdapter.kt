package uz.group1.maxwayapp.presentation.screens.profile.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.sources.remote.response.AddressData
import uz.group1.maxwayapp.databinding.ItemAddressBinding

class AddressAdapter(private val onDeleteClick: (AddressData) -> Unit) : ListAdapter<AddressData, AddressAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddressData) {
            binding.tvAddressName.text = item.name
            binding.tvCoords.text = "%.5f, %.5f".format(item.latitude, item.longitude)
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<AddressData>() {
            override fun areItemsTheSame(oldItem: AddressData, newItem: AddressData) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: AddressData, newItem: AddressData) = oldItem == newItem
        }
    }
}