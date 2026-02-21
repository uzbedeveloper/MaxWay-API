package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.http.GET
import uz.group1.maxwayapp.data.sources.remote.response.AdsResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAllBanner(): AdsResponse
}

// search, product, category, ads, recomen, basket, history