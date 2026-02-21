package uz.group1.maxwayapp.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.response.NotificationResponse
import uz.group1.maxwayapp.data.sources.remote.response.StoriesResponse

interface NotificationsApi {
    @GET("notifications")
    suspend fun getNotifications(): Response<NotificationResponse>
}