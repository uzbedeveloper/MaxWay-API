package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.sources.remote.response.AddressData

interface AddressRepository {
    suspend fun getAddresses(): Result<List<AddressData>>
    suspend fun addAddress(name: String, latitude: Double, longitude: Double): Result<AddressData>
    suspend fun editAddress(id: Int, name: String, latitude: Double, longitude: Double): Result<AddressData>
    suspend fun deleteAddress(id: Int): Result<String>
}