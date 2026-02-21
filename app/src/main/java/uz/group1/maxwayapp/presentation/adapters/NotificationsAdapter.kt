package uz.group1.maxwayapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.databinding.ItemNotificationBinding
import uz.group1.maxwayapp.utils.loadImage

class NotificationsAdapter(private val notifications: List<NotificationUiData>) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NotificationUiData){
            binding.imageLoader.isVisible = true
            binding.img.loadImage(item.imgURL){
                binding.imageLoader.isVisible = false
            }
            binding.title.text = item.name
            binding.subtitle.text = item.message
            binding.time.text = item.sendDate
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = notifications[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = notifications.size
}