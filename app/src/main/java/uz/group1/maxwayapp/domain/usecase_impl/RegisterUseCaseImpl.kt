package uz.group1.maxwayapp.domain.usecase_impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.usecase.RegisterUseCase

class RegisterUseCaseImpl(
    private val repository: AuthRepository
) : RegisterUseCase {

    companion object {
        private const val TAG = "RegisterUseCaseImpl"
    }

    override fun sendPhone(phone: String): Flow<Result<RegisterResponse>> = flow {
        Log.d(TAG, "sendPhone() chaqirildi → phone: $phone")
        val result = repository.register(phone)

        result.onSuccess { response ->
            Log.d(TAG, "SMS muvaffaqiyatli yuborildi! Phone: ${response.phone}, SMS Code: ${response.code}")
        }.onFailure {
            Log.e(TAG, "SMS yuborishda xatolik: ${it.message}")
        }

        emit(result)
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun verifyCode(phone: String, code: Int): Flow<Result<String>> = flow {
        Log.d(TAG, "verifyCode() chaqirildi → phone: $phone, code: $code")
        val result = repository.verify(phone, code)
        emit(result)
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun resendCode(phone: String): Flow<Result<Unit>> = flow {
        Log.d(TAG, "resendCode() chaqirildi → phone: $phone")
        val result = repository.repeatSms(phone)
        emit(result)
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}
