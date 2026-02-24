package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import uz.group1.maxwayapp.data.sources.remote.request.RegisterRequest
import uz.group1.maxwayapp.data.sources.remote.request.RepeatRequest
import uz.group1.maxwayapp.data.sources.remote.request.UserDataRequest
import uz.group1.maxwayapp.data.sources.remote.request.VerifyRequest
import uz.group1.maxwayapp.data.sources.remote.response.GeneralResponse
import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse
import uz.group1.maxwayapp.data.sources.remote.response.UserDataResponse
import uz.group1.maxwayapp.data.sources.remote.response.UserDeleteResponse
import uz.group1.maxwayapp.data.sources.remote.response.VerifyResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(@Body data: RegisterRequest): Response<GeneralResponse<RegisterResponse>>
    @POST("/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<GeneralResponse<VerifyResponse>>
    @POST("/repeat")
    suspend fun repeat(@Body data: RepeatRequest): Response<GeneralResponse<RegisterResponse>>

    @PUT("/update_user_info")
    suspend fun updateUserInfo(@Header("token") token: String, @Body data: UserDataRequest): Response<GeneralResponse<UserDataResponse>>

    @GET("/user_info")
    suspend fun getUserInfo(@Header("token") token: String): Response<GeneralResponse<UserDataResponse>>

    @DELETE("/delete_account")
    suspend fun deleteAccount(@Header("token") token: String): Response<GeneralResponse<UserDeleteResponse>>


}

// register, account crud, verify