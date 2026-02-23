package uz.group1.maxwayapp.data.repository_impl

import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.mapper.toDetailsUIdata
import uz.group1.maxwayapp.data.mapper.toMapUiData
import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.data.model.FilialMapUIData
import uz.group1.maxwayapp.data.sources.remote.api.BranchApi
import uz.group1.maxwayapp.domain.repository.BranchRepository

class BranchRepositoryImpl(private val branchApi: BranchApi): BranchRepository {

    companion object{
        private lateinit var instance: BranchRepository

        fun getInstance(): BranchRepository {
            if (!::instance.isInitialized) {
                instance = BranchRepositoryImpl(ApiClient.branchApi)
            }
            return instance
        }
    }
    override suspend fun getBranchList(): Result<List<FilialListUIData>> {
        return try {
            val response = branchApi.getFilial()
            Result.success(response.data.map { it.toDetailsUIdata() })
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getBranchMapData(): Result<List<FilialMapUIData>> {
        return try {
            val response = branchApi.getFilial()
            Result.success(response.data.map { it.toMapUiData() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}