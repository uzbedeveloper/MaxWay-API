package uz.group1.maxwayapp.data.repository_impl

import com.google.gson.Gson
import timber.log.Timber
import uz.group1.maxwayapp.app.MyApp
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.data.sources.remote.api.AuthApi
import uz.group1.maxwayapp.data.sources.remote.request.RegisterRequest
import uz.group1.maxwayapp.data.sources.remote.request.RepeatSmsRequest
import uz.group1.maxwayapp.data.sources.remote.request.VerifyRequest
import uz.group1.maxwayapp.data.sources.remote.response.ErrorMessageResponse
import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse
import uz.group1.maxwayapp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager,
    private val gson: Gson = Gson()
) : AuthRepository {

    override suspend fun register(phone: String): Result<RegisterResponse> {
        return try {
            Timber.d("register() chaqirildi → phone: $phone")
            val response = authApi.register(RegisterRequest(phone))

            if (response.isSuccessful) {
                val body = response.body()
                if (body?.data != null) {
                    Timber.d("register() muvaffaqiyatli, SMS kodi: ${body.data.code}")
                    Result.success(body.data)
                } else {
                    Result.failure(Exception("Ma'lumot topilmadi"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorException = parseError(errorBody, response.message())
                Timber.e("register() xatolik: ${errorException.message} (Raw: $errorBody)")
                Result.failure(errorException)
            }
        } catch (e: Exception) {
            Timber.e("register() exception: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun verify(phone: String, code: Int): Result<String> {
        return try {
            Timber.d("verify() chaqirildi → phone: $phone, code: $code")
            val response = authApi.verify(VerifyRequest(phone, code))

            if (response.isSuccessful) {
                val token = response.body()?.data?.token
                if (token != null) {
                    tokenManager.saveToken(token)
                    Timber.d("verify() muvaffaqiyatli → token saqlandi")
                    Result.success(token)
                } else {
                    Timber.e("verify() token null keldi")
                    Result.failure(Exception("Token kelmadi (Null body)"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorException = parseError(errorBody, response.message())
                Timber.e("verify() xatolik: ${errorException.message} (Raw: $errorBody)")
                Result.failure(errorException)
            }
        } catch (e: Exception) {
            Timber.e("verify() exception: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun repeatSms(phone: String): Result<Unit> {
        return try {
            Timber.d("repeatSms() chaqirildi → phone: $phone")
            val response = authApi.repeatSms(RepeatSmsRequest(phone))

            if (response.isSuccessful) {
                Timber.d("repeatSms() muvaffaqiyatli")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorException = parseError(errorBody, response.message())
                Timber.e("repeatSms() xatolik: ${errorException.message} (Raw: $errorBody)")
                Result.failure(errorException)
            }
        } catch (e: Exception) {
            Timber.e("repeatSms() exception: ${e.message}")
            Result.failure(e)
        }
    }

    private fun parseError(errorJson: String?, defaultMessage: String): Throwable {
        if (errorJson.isNullOrEmpty()) {
            return Throwable(if (defaultMessage.isNullOrEmpty()) "Noma'lum xatolik" else defaultMessage)
        }
        
        return try {
            val errorResponse = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
            if (!errorResponse.message.isNullOrEmpty()) {
                Throwable(errorResponse.message)
            } else {
                Throwable("Xatolik: $errorJson")
            }
        } catch (e: Exception) {
            Throwable(errorJson.take(100))
        }
    }

    companion object {
        private var instance: AuthRepositoryImpl? = null

        fun init() {
            if (instance == null) {
                instance = AuthRepositoryImpl(
                    ApiClient.authApi,
                    TokenManager(MyApp.instance)
                )
            }
        }

        fun getInstance(): AuthRepositoryImpl {
            return instance ?: throw RuntimeException("AuthRepositoryImpl must be initialized")
        }
    }
}