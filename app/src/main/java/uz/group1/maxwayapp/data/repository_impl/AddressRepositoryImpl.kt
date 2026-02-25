package uz.group1.maxwayapp.data.repository_impl

import com.google.gson.Gson
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.data.sources.remote.api.AddressApi
import uz.group1.maxwayapp.data.sources.remote.request.AddressEditRequest
import uz.group1.maxwayapp.data.sources.remote.request.AddressRequest
import uz.group1.maxwayapp.data.sources.remote.response.AddressData
import uz.group1.maxwayapp.data.sources.remote.response.ErrorMessageResponse
import uz.group1.maxwayapp.domain.repository.AddressRepository

class AddressRepositoryImpl(private val addressApi: AddressApi, private val gson: Gson) : AddressRepository {
    companion object {
        private lateinit var instance: AddressRepository

        fun getInstance(): AddressRepository {
            if (!(::instance.isInitialized)) {
                instance = AddressRepositoryImpl(ApiClient.addressApi, Gson())
            }
            return instance
        }
    }
    override suspend fun getAddresses(): Result<List<AddressData>> {
        return try {
            val token = TokenManager.getToken() ?: return Result.failure(Throwable("Siz ro‘yxatdan o‘tmagansiz. Iltimos, ro‘yxatdan o‘ting"))
            val response = addressApi.getAddresses(token)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addAddress(name: String, latitude: Double, longitude: Double): Result<AddressData> {
        return try {
            val token = TokenManager.getToken() ?: return Result.failure(Throwable("Siz ro‘yxatdan o‘tmagansiz. Iltimos, ro‘yxatdan o‘ting"))
            val response = addressApi.addAddress(token, AddressRequest(name, latitude, longitude))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editAddress(id: Int, name: String, latitude: Double, longitude: Double): Result<AddressData> {
        return try {
            val token = TokenManager.getToken() ?: return Result.failure(Throwable("Siz ro‘yxatdan o‘tmagansiz. Iltimos, ro‘yxatdan o‘ting"))
            val response = addressApi.editAddress(token, AddressEditRequest(id, name, latitude, longitude))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Throwable(parseError(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAddress(id: Int): Result<String> {
        return try {
            val token = TokenManager.getToken() ?: return Result.failure(Throwable("Siz ro‘yxatdan o‘tmagansiz. Iltimos, ro‘yxatdan o‘ting"))
            val response = addressApi.deleteAddress(token, id)
            if (response.isSuccessful) {
                Result.success(response.body()?.message ?: "Address deleted")
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
            error?.message ?: json ?: "Noma'lum xatolik"
        } catch (e: Exception) { json ?: "Noma'lum xatolik" }
    }


}