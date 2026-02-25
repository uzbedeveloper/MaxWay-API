package uz.group1.maxwayapp.data.sources.remote.response.recomended_product

data class RecomendedProductItem(
    val categoryID: Int,
    val cost: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String
)