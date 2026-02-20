package uz.group1.maxwayapp.data.repository_impl

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.data.sources.remote.api.ProductApi
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.repository.ProductRepository

class ProductRepositoryImpl (private val productApi: ProductApi): ProductRepository {

    companion object {
        private lateinit var instance: ProductRepository

        fun init() {
            if (!(::instance.isInitialized))
                instance = ProductRepositoryImpl(ApiClient.productApi)
        }

        fun getInstance() : ProductRepository = instance
    }
    override suspend fun getBanners(): Result<List<BannerUIData>> {
        return try {
            val response = productApi.getAllBanner()

            val list = response.data.map {
                BannerUIData(
                    id = it.id,
                    bannerUrl = it.bannerUrl
                )
            }

            Result.success(list)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategories(): Result<List<CategoryUIData>> {
        return try {
            val response = productApi.getAllCategories()
            val list = response.data.map{
                CategoryUIData(
                    id = it.id,
                    name = it.name,
                    products = emptyList()
                )
            }
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getCategoriesWithProducts(): Result<List<CategoryUIData>> {
        return try {
            val categoriesResponse = productApi.getAllCategories()
            val productsResponse = productApi.getAllProducts()

            val categories = categoriesResponse.data
            val products = productsResponse.data

            val productMap = products.groupBy { it.categoryID }

            val result = categories.map {category ->
                CategoryUIData(
                    id = category.id,
                    name = category.name,
                    products = productMap[category.id]?.map{product ->
                        ProductUIData(
                            id = product.id,
                            categoryID = product.categoryID,
                            name = product.name,
                            description = product.description,
                            image = product.image,
                            cost = product.cost
                        )
                    } ?: emptyList()
                )

            }
            Result.success(result)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

}