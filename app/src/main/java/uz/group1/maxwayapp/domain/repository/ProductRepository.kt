package uz.group1.maxwayapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.data.model.ProductSearchUIData
import uz.group1.maxwayapp.data.sources.remote.request.orders.createOrder.CreateOrderRequest
import uz.group1.maxwayapp.data.sources.remote.response.order.createOrder.CreateOrderResponse
import uz.group1.maxwayapp.data.sources.remote.response.recomended_product.RecomendedProductResponse

interface ProductRepository {

    suspend fun getBanners(): Result<List<BannerUIData>>
    suspend fun getCategories(): Result<List<CategoryUIData>>
    suspend fun getCategoriesWithProducts(): Result<List<CategoryUIData>>

    fun getMenu() : Flow<List<CategoryUIData>>
    fun updateProductCount(productId: Int, newCount: Int)
    suspend fun getSearchProducts(str: String): Result<List<ProductSearchUIData>>
    suspend fun confirmOrder(request: CreateOrderRequest): Result<CreateOrderResponse>
    fun clearCart()

    suspend fun getMyOrders(): Result<List<MyOrdersUIData>>
    suspend fun getRecommendedProducts(ids:List<Int>): Result<RecomendedProductResponse>

    fun hasToken(): Boolean
    fun clear()
}