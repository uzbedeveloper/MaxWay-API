package uz.group1.maxwayapp.data.repository_impl

import com.google.gson.Gson
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.sources.remote.api.AuthApi
import uz.group1.maxwayapp.data.sources.remote.request.RegisterRequest
import uz.group1.maxwayapp.data.sources.remote.response.ErrorMessageResponse
import uz.group1.maxwayapp.domain.repository.AuthRepository

class AuthRepositoryImpl private constructor(
    private val authApi: AuthApi,
    private val gson: Gson
) : AuthRepository {

    companion object {
        private lateinit var instance: AuthRepository

        fun getInstance() : AuthRepository {
            if (!(::instance.isInitialized))
                instance = AuthRepositoryImpl(ApiClient.authApi, Gson())

            return instance
        }
    }


    override suspend fun register(phone: String): Result<Unit> {
        val request = RegisterRequest(phone)
        val response = authApi.register(request)
        return if (response.isSuccessful && response.body() != null)
            Result.success(Unit)
        else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }
}