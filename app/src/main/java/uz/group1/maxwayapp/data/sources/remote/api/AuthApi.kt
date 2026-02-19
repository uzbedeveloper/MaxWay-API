package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.group1.maxwayapp.data.sources.remote.request.RegisterRequest
import uz.group1.maxwayapp.data.sources.remote.response.GeneralResponse
import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(@Body data: RegisterRequest): Response<GeneralResponse<RegisterResponse>>
}

// register, account crud, verify