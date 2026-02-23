package uz.group1.maxwayapp.presentation.screens.profile.filial.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.databinding.ItemListBinding

class FilialListAdapter: ListAdapter<FilialListUIData, FilialListAdapter.FilialViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilialViewHolder {
        return FilialViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: FilialViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<FilialListUIData>(){
            override fun areItemsTheSame(oldItem: FilialListUIData, newItem: FilialListUIData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FilialListUIData, newItem: FilialListUIData): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class FilialViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(listData: FilialListUIData){
            binding.apply {
                textName.text = listData.name
                textAddress.text = listData.address
                textPhone.text = listData.phone
                val timeRange = "${listData.openTime.replace(" ", "")}-${listData.closeTime.replace(" ", "")}"
                textWorkTime.text = timeRange
                textDeliveryTime.text = timeRange
            }
        }
    }
}