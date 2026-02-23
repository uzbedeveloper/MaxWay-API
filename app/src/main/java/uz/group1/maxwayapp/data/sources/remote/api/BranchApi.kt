package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.http.GET
import uz.group1.maxwayapp.data.sources.remote.response.FilialResponse

interface BranchApi {
    @GET("branches")
    suspend fun getFilial(): FilialResponse
}

// filiallar