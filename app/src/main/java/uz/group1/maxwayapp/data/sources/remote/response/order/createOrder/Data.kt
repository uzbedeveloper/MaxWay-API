package uz.group1.maxwayapp.data.sources.remote.response.order.createOrder

data class Data(
    val address: String,
    val createTime: Long,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val ls: List<L>,
    val sum: Int,
    val userID: Int
)