package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import uz.group1.maxwayapp.data.sources.remote.request.AddressEditRequest
import uz.group1.maxwayapp.data.sources.remote.request.AddressRequest
import uz.group1.maxwayapp.data.sources.remote.response.AddressData
import uz.group1.maxwayapp.data.sources.remote.response.GeneralResponse

interface AddressApi {

    @GET("/address")
    suspend fun getAddresses(@Header("token") token: String): Response<GeneralResponse<List<AddressData>>>

    @POST("/address/add")
    suspend fun addAddress(@Header("token") token: String, @Body data: AddressRequest): Response<GeneralResponse<AddressData>>

    @POST("/address/edit")
    suspend fun editAddress(@Header("token") token: String, @Body data: AddressEditRequest): Response<GeneralResponse<AddressData>>

    @POST("/address/delete")
    suspend fun deleteAddress(@Header("token") token: String, @Query("id") id: Int): Response<GeneralResponse<String>>
}