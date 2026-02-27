package uz.group1.maxwayapp.presentation.screens.cart_details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.databinding.ScreenOrderDetailBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarConfig
import uz.group1.maxwayapp.presentation.screens.base_fragment.SystemBarIconStyle
import uz.group1.maxwayapp.utils.loadImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailScreen : BaseFragment(R.layout.screen_order_detail) {

    private val binding by viewBinding(ScreenOrderDetailBinding::bind)

    override val systemBarConfig = SystemBarConfig(
        statusBarIcons = SystemBarIconStyle.DARK_ICONS,
        navigationBarIcons = SystemBarIconStyle.DARK_ICONS,
        fullscreen = false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentOrder = arguments?.getSerializable("currentOrder") as? MyOrdersUIData

        if (currentOrder != null) {
            setupOrderDetails(currentOrder)
            val currentTime = System.currentTimeMillis()
            val diffInMs = currentTime - currentOrder.createTime
            val minutesPassed = (diffInMs / (1000 * 60)).toInt()

            val currentStage = when {
                minutesPassed < 5 -> 1
                minutesPassed < 10 -> 2
                minutesPassed < 20 -> 3
                else -> 4
            }
            setupStatusProgress(currentStage)
        }

    }

    private fun setupOrderDetails(order: MyOrdersUIData) {
        binding.apply {
            tvOrderNumber.text = "Заказ №${order.orderNumber}"
            tvOrderDate.text = formatTimestamp(order.createTime)
            tvPaymentMethod.text = "Наличные"
            imgOrderBanner.loadImage(order.ls[0].productData.image){

            }

            containerItems.removeAllViews()

            order.ls.forEach { item ->
                val itemView =
                    layoutInflater.inflate(R.layout.item_order_detail_row, containerItems, false)
                val name = itemView.findViewById<TextView>(R.id.tv_item_name)
                val price = itemView.findViewById<TextView>(R.id.tv_item_price)

                name.text = "${item.productData.name}  ${item.count}x"
                price.text = String.format("%,d сум", item.productData.cost * item.count)

                containerItems.addView(itemView)
            }

            val deliveryCost = 6000
            tvTotalAmount.text = String.format("%,d сум", order.sum + deliveryCost)
        }
    }

    private fun setupStatusProgress(stage: Int) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.purple)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.bg_gray)
        val white = ContextCompat.getColor(requireContext(), R.color.white)
        val grayIcon = ContextCompat.getColor(requireContext(), R.color.black)

        fun updateStage(imageView: ImageView, isActive: Boolean) {
            val bgColor = if (isActive) activeColor else inactiveColor
            val iconColor = if (isActive) white else grayIcon

            imageView.backgroundTintList = ColorStateList.valueOf(bgColor)
            imageView.imageTintList = ColorStateList.valueOf(iconColor)
        }

        fun updateLine(view: View, isActive: Boolean) {
            view.backgroundTintList = ColorStateList.valueOf(if (isActive) activeColor else inactiveColor)
        }

        binding.apply {
            updateStage(stage1, stage >= 1)

            updateLine(line1, stage >= 2)
            updateStage(stage2, stage >= 2)

            updateLine(line2, stage >= 3)
            updateStage(stage3, stage >= 3)

            updateLine(line3, stage >= 4)
            updateStage(stage4, stage >= 4)

            tvDesc.text = when(stage) {
                1 -> "Заказ принят"
                2 -> "Готовится"
                3 -> "В пути"
                4 -> "Доставлено"
                else -> "Обработка..."
            }
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}