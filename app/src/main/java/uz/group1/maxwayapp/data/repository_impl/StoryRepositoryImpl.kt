package uz.group1.maxwayapp.data.repository_impl

import android.util.Log
import com.google.gson.Gson
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.mapper.toUiData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.api.StoriesApi
import uz.group1.maxwayapp.data.sources.remote.response.StoriesResponse
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.repository.StoryRepository

class StoryRepositoryImpl(
    private val storyApi: StoriesApi,
    private val gson: Gson) : StoryRepository {

    companion object {
        private lateinit var instance: StoryRepository

        fun init() {
            if (!(::instance.isInitialized))
                instance = StoryRepositoryImpl(ApiClient.storiesApi,Gson())
        }

        fun getInstance() : StoryRepository = instance
    }

    override suspend fun getStories(): Result<List<StoryUIData>> {
        return try {
            val response = storyApi.getStories()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val uiList = body.data.map { it.toUiData() }
                    Result.success(uiList)
                } else {
                    Result.failure(Throwable("Response body is null"))
                }
            } else {
                Result.failure(Throwable("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}