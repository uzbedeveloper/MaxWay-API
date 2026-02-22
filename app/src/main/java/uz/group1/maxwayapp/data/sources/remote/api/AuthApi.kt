package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.group1.maxwayapp.data.sources.remote.request.RegisterRequest
import uz.group1.maxwayapp.data.sources.remote.request.RepeatSmsRequest
import uz.group1.maxwayapp.data.sources.remote.request.VerifyRequest
import uz.group1.maxwayapp.data.sources.remote.response.GeneralResponse
import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse
import uz.group1.maxwayapp.data.sources.remote.response.VerifyResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(@Body data: RegisterRequest): Response<GeneralResponse<RegisterResponse>>

    @POST("/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<GeneralResponse<VerifyResponse>>

    @POST("/resend")
    suspend fun repeatSms(@Body data: RepeatSmsRequest): Response<GeneralResponse<Unit>>
}