package uz.group1.maxwayapp.data.sources.remote.response.recomended_product

data class RecomendedProductResponse(
    val data: List<RecomendedProductItem>,
    val message: String
)