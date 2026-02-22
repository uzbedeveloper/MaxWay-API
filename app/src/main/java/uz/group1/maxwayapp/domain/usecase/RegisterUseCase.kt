package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse

interface RegisterUseCase {
    fun sendPhone(phone: String): Flow<Result<RegisterResponse>>
    fun verifyCode(phone: String, code: Int): Flow<Result<String>>
    fun resendCode(phone: String): Flow<Result<Unit>>
}