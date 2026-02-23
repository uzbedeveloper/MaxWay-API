package uz.group1.maxwayapp.domain.models

import uz.group1.maxwayapp.data.model.ProductUIData

sealed class HomeItem {
    data class CategoryHeader(val id: Int, val name: String) : HomeItem()
    data class ProductItem(val product: ProductUIData) : HomeItem()
}