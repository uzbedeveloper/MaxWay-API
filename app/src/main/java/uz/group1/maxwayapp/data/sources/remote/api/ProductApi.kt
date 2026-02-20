package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.http.GET
import uz.group1.maxwayapp.data.sources.remote.response.AdsResponse
import uz.group1.maxwayapp.data.sources.remote.response.CategoriesResponse
import uz.group1.maxwayapp.data.sources.remote.response.ProductsResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAllBanner(): AdsResponse

    @GET("products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("categories")
    suspend fun getAllCategories(): CategoriesResponse
}

// search, product, category, ads, recomen, basket, history