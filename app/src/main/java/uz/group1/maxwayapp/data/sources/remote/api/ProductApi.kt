package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.group1.maxwayapp.data.sources.remote.response.AdsResponse
import uz.group1.maxwayapp.data.sources.remote.response.CategoriesResponse
import uz.group1.maxwayapp.data.sources.remote.response.ProductSearchResponse
import uz.group1.maxwayapp.data.sources.remote.response.ProductsResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAllBanner(): AdsResponse
    @GET("products")
    suspend fun getAllProducts(): ProductsResponse
    @GET("categories")
    suspend fun getAllCategories(): CategoriesResponse
    @GET("products_by_query")
    suspend fun getSearchProduct(@Query("query") query: String): ProductSearchResponse
}

// search, product, category, ads, recomen, basket, history