package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.ProductSearchUIData

interface ProductRepository {
    suspend fun getBanners(): Result<List<BannerUIData>>
    suspend fun getCategories(): Result<List<CategoryUIData>>
    suspend fun getCategoriesWithProducts(): Result<List<CategoryUIData>>
    suspend fun getSearchProducts(str: String): Result<List<ProductSearchUIData>>
}