package uz.group1.maxwayapp.data.sources.remote.request.orders.createOrder

data class CreateOrderRequest(
    val address: String = "TEST",
    val latitude: String = "41.00",
    val longitude: String = "69.00",
    val ls: List<OrderItem>
)