package uz.group1.maxwayapp.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import uz.group1.maxwayapp.data.sources.local.TokenManager

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        val originalRequest = chain.request()

        return if (token != null) {
            Timber.d("AuthInterceptor: Token header qo'shildi: Bearer $token")
            val newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            Timber.d("AuthInterceptor: Token yo'q, so'rov tokensiz ketdi")
            chain.proceed(originalRequest)
        }
    }
}