package uz.group1.maxwayapp.data.repository_impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.mapper.toBannerUIData
import uz.group1.maxwayapp.data.mapper.toCategoryUIData
import uz.group1.maxwayapp.data.mapper.toUIData
import uz.group1.maxwayapp.data.mapper.toUISData
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.ProductSearchUIData
import uz.group1.maxwayapp.data.sources.remote.api.ProductApi
import uz.group1.maxwayapp.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productApi: ProductApi): ProductRepository {

    private val _menuFlow = MutableStateFlow<List<CategoryUIData>>(emptyList())
    val menuFlow: StateFlow<List<CategoryUIData>> = _menuFlow.asStateFlow()

    override fun getMenu(): Flow<List<CategoryUIData>> {
        return menuFlow
    }

    override fun updateProductCount(productId: Int, newCount: Int) {
        val currentMenu = _menuFlow.value.map { category ->
            category.copy(products = category.products.map { product ->
                if (product.id == productId) product.copy(count = newCount) else product
            })
        }
        _menuFlow.value = currentMenu
    }

    companion object{
        private lateinit var instance: ProductRepository

        fun getInstance(): ProductRepository {
            if (!::instance.isInitialized) {
                instance = ProductRepositoryImpl(ApiClient.productApi)
            }
            return instance
        }
    }
    override suspend fun getBanners(): Result<List<BannerUIData>> {
        return try {
            val response = productApi.getAllBanner()
            val list = response.data.map { it.toBannerUIData() }
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getCategories(): Result<List<CategoryUIData>> {
        return try {
            val response = productApi.getAllCategories()
            val list= response.data.map { it.toCategoryUIData() }

            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getCategoriesWithProducts(): Result<List<CategoryUIData>> {
        return try {
            val categoriesResponse = productApi.getAllCategories()
            val productResponse = productApi.getAllProducts()

            val categories = categoriesResponse.data
            val products = productResponse.data

            val productMap = products.groupBy { it.categoryID }

            val result = categories.map { category->
                val categoryProduct = productMap[category.id] ?: emptyList()
                category.toUIData(categoryProduct)
            }
            _menuFlow.value = result
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getSearchProducts(str: String): Result<List<ProductSearchUIData>> {
        return try {
            val response = productApi.getSearchProduct(str)
            val list = response.data.map { it.toUISData() }
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}