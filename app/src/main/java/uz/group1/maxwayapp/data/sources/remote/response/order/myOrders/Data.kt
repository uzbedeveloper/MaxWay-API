package uz.group1.maxwayapp.data.sources.remote.response.order.myOrders

data class Data(
    val address: String,
    val createTime: Long,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val ls: List<ProductItem>,
    val sum: Int,
    val userID: Int
)