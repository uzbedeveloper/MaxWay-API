package uz.group1.maxwayapp.data.sources.remote.response

data class ProductResponse(
    val id: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)
