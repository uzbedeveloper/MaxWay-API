package uz.group1.maxwayapp.data.repository_impl

import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.mapper.toBannerUIData
import uz.group1.maxwayapp.data.mapper.toCategoryUIData
import uz.group1.maxwayapp.data.mapper.toUIData
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.sources.remote.api.ProductApi
import uz.group1.maxwayapp.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productApi: ProductApi): ProductRepository {

    companion object{
        private lateinit var instance: ProductRepository

        fun init(){
            if (!(::instance.isInitialized)){
                instance = ProductRepositoryImpl(ApiClient.productApi)
            }
        }
        fun getInstance(): ProductRepository = instance
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
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}