package uz.group1.maxwayapp.data.model

import uz.group1.maxwayapp.data.sources.remote.response.order.myOrders.ProductItem
import java.io.Serializable

data class MyOrdersUIData(
    val address: String,
    val createTime: Long,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val ls: List<ProductItem>,
    val sum: Int,
    val userID: Int,
    val currentStage: Int,
    val orderNumber: String,
    val statusText: String,
): Serializable