package uz.group1.maxwayapp.data.repository_impl

import com.google.gson.Gson
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.data.sources.remote.api.AuthApi
import uz.group1.maxwayapp.data.sources.remote.request.*
import uz.group1.maxwayapp.data.sources.remote.response.*
import uz.group1.maxwayapp.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val authApi: AuthApi, private val gson: Gson) : AuthRepository {
    @Inject
    lateinit var tokenManager: TokenManager

    override suspend fun register(phone: String): Result<String> {
        return try {
            val response = authApi.register(RegisterRequest(phone))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data.phone)
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verify(phone: String, code: Int): Result<Boolean> {
        return try {
            val response = authApi.verify(VerifyRequest(phone, code))
            val errorBody = response.errorBody()?.string()
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.data.token
                tokenManager.saveUserData(token, null, null, null)
                val userInfoResponse = authApi.getUserInfo(token)

                if (userInfoResponse.isSuccessful && userInfoResponse.body() != null) {
                    val userData = userInfoResponse.body()!!.data
                    tokenManager.saveUserData(null, userData.name, userData.phone, userData.birthDate)
                    val hasProfile = !userData.name.isNullOrEmpty() && !userData.birthDate.isNullOrEmpty()
                    Result.success(hasProfile)
                } else {
                    Result.success(false)
                }
            } else {
                Result.failure(Throwable(parseError(errorBody)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun repeat(phone: String): Result<String> {
        return try {
            val response = authApi.repeat(RepeatRequest(phone))
            val errorBody = response.errorBody()?.string()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data.phone)
            } else {
                Result.failure(Throwable(parseError(errorBody)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserInfo(name: String, birthDate: String): Result<UserDataResponse> {
        return try {
            val token = tokenManager.getToken() ?: ""
            val response = authApi.updateUserInfo(token, UserDataRequest(name, birthDate))
            if (response.isSuccessful) {
                val data = response.body()?.data
                if (data != null) {
                    tokenManager.saveUserData(null, data.name, data.phone, data.birthDate)
                    Result.success(data)
                } else {
                    tokenManager.saveUserData(null, name, null, birthDate)
                    Result.success(UserDataResponse(0, name, null, birthDate, null))
                }
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(): Result<UserDataResponse> {
        return try {
            val token = tokenManager.getToken() ?: ""
            val response = authApi.getUserInfo(token)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.data
                tokenManager.saveUserData(null, data.name, data.phone, data.birthDate)
                Result.success(data)
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<String> {
        return try {
            val token = tokenManager.getToken() ?: ""
            val response = authApi.deleteAccount(token)
            if (response.isSuccessful) {
                tokenManager.clear()
                Result.success(response.body()?.message ?: "Account deleted")
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseError(json: String?): String {
        return try {
            val error = gson.fromJson(json, ErrorMessageResponse::class.java)
            error.message
        } catch (e: Exception) { "Noma'lum xatolik: $e" }
    }
}