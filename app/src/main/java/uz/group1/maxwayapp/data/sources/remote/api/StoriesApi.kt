package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.response.StoriesResponse

interface StoriesApi {
    @GET("stories")
    suspend fun getStories(): Response<StoriesResponse>
}