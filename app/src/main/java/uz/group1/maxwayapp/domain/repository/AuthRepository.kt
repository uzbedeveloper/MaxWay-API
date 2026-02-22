package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.sources.remote.response.RegisterResponse

interface AuthRepository {
    suspend fun register(phone: String): Result<RegisterResponse>
    suspend fun verify(phone: String, code: Int): Result<String>
    suspend fun repeatSms(phone: String): Result<Unit>
}