package uz.group1.maxwayapp.data.sources.remote.response.order.createOrder

data class ProductData(
    val categoryID: Int,
    val cost: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String
)