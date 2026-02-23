package uz.group1.maxwayapp.data.model

data class CategoryUIData(
    val id: Int,
    val name: String,
    val products: List<ProductUIData>,
    val count:Int
)