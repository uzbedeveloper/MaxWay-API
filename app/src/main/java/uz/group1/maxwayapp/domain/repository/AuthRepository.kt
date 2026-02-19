package uz.group1.maxwayapp.domain.repository

interface AuthRepository {

    /**
     *  bu method user register qilish uchun
     */
    suspend fun register(phone: String) : Result<Unit>
}