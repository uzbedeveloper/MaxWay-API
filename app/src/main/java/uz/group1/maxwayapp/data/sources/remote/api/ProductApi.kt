package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import uz.group1.maxwayapp.data.sources.remote.request.orders.createOrder.CreateOrderRequest
import uz.group1.maxwayapp.data.sources.remote.response.AdsResponse
import uz.group1.maxwayapp.data.sources.remote.response.CategoriesResponse
import uz.group1.maxwayapp.data.sources.remote.response.ProductSearchResponse
import uz.group1.maxwayapp.data.sources.remote.response.ProductsResponse
import uz.group1.maxwayapp.data.sources.remote.response.order.createOrder.CreateOrderResponse
import uz.group1.maxwayapp.data.sources.remote.response.order.myOrders.MyOrdersResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAllBanner(): AdsResponse
    @GET("products")
    suspend fun getAllProducts(): ProductsResponse
    @GET("categories")
    suspend fun getAllCategories(): CategoriesResponse
    @GET("products_by_query")
    suspend fun getSearchProduct(@Query("query") query: String): ProductSearchResponse

    @POST("create_order")
    suspend fun createOrder(@Header("token") token: String, @Body request: CreateOrderRequest): CreateOrderResponse

    @GET("my_orders")
    suspend fun getAllOrders(@Header("token") token:String): MyOrdersResponse

}

// search, product, category, ads, recomen, basket, history