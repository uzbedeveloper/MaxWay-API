package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.sources.remote.response.UserDataResponse

interface AuthRepository {
    suspend fun register(phone: String): Result<String>
    suspend fun verify(phone: String, code: Int): Result<Boolean>
    suspend fun repeat(phone: String): Result<String>
    suspend fun updateUserInfo(name: String, birthDate: String): Result<UserDataResponse>
    suspend fun getUserInfo(): Result<UserDataResponse>
    suspend fun deleteAccount(): Result<String>
}