package uz.group1.maxwayapp.data.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.group1.maxwayapp.data.network.dto.request.RegisterRequest
import uz.group1.maxwayapp.data.network.dto.request.RepeatSmsRequest
import uz.group1.maxwayapp.data.network.dto.request.VerifyRequest
import uz.group1.maxwayapp.data.network.dto.response.AuthResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<AuthResponse>

    @POST("/verify")
    suspend fun verify(
        @Body body: VerifyRequest
    ): Response<AuthResponse>

    @POST("/repeat")
    suspend fun repeatSms(
        @Body body: RepeatSmsRequest
    ): Response<AuthResponse>
}