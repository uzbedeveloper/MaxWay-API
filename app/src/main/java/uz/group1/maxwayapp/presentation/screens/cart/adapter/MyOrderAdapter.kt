package uz.group1.maxwayapp.presentation.screens.cart.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.databinding.ItemMyOrderBinding

class MyOrderAdapter : ListAdapter<MyOrdersUIData, MyOrderAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemMyOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: MyOrdersUIData) {
            val currentTime = System.currentTimeMillis()
            val diffInMs = currentTime - order.createTime
            val minutesPassed = (diffInMs / (1000 * 60)).toInt()

            val currentStage = when {
                minutesPassed < 5 -> 1
                minutesPassed < 10 -> 2
                minutesPassed < 20 -> 3
                else -> 4
            }

            binding.statusZakaz.text = "Buyurtma №${order.createTime.toString().takeLast(4)}"

            binding.tvDesc.text = when(currentStage) {
                1 -> "Qabul qilindi"
                2 -> "Tayyorlanmoqda"
                3 -> "Yo'lda"
                else -> "Yetkazildi"
            }
            updateStages(currentStage)
        }

        private fun updateStages(stage: Int) {
            val context = binding.root.context
            val purple = ContextCompat.getColor(context, R.color.purple_1200)
            val gray = Color.parseColor("#F1F1F1")
            
            val stages = arrayOf(binding.stage1, binding.stage2, binding.stage3, binding.stage4)
            val lines = arrayOf(binding.line1, binding.line2, binding.line3)

            stages.forEachIndexed { index, imageView ->
                val currentIdx = index + 1
                if (currentIdx <= stage) {
                    imageView.setBackgroundResource(R.drawable.progress_circle_bg_purple)
                    imageView.setColorFilter(Color.WHITE)
                } else {
                    imageView.setBackgroundResource(R.drawable.progress_circle_bg)
                    imageView.setColorFilter(Color.BLACK)
                }
            }

            lines.forEachIndexed { index, view ->
                val lineStage = index + 2
                if (lineStage <= stage) {
                    view.backgroundTintList = ColorStateList.valueOf(purple)
                } else {
                    view.backgroundTintList = ColorStateList.valueOf(gray)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMyOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<MyOrdersUIData>() {
        override fun areItemsTheSame(oldItem: MyOrdersUIData, newItem: MyOrdersUIData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MyOrdersUIData, newItem: MyOrdersUIData) = oldItem == newItem
    }
}