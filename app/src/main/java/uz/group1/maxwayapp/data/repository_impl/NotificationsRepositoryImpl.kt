package uz.group1.maxwayapp.data.repository_impl

import com.google.gson.Gson
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.mapper.toUiData
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.api.NotificationsApi
import uz.group1.maxwayapp.data.sources.remote.api.StoriesApi
import uz.group1.maxwayapp.domain.repository.NotificationsRepository
import uz.group1.maxwayapp.domain.repository.StoryRepository

class NotificationsRepositoryImpl(
    private val notificationsApi: NotificationsApi,
    private val gson: Gson) : NotificationsRepository {

    companion object {
        private lateinit var instance: NotificationsRepository

        fun getInstance() : NotificationsRepository {
            if (!(::instance.isInitialized))
                instance = NotificationsRepositoryImpl(ApiClient.notificationsApi,Gson())

            return instance
        }
    }

    override suspend fun getNotifications(): Result<List<NotificationUiData>> {
        return try {
            val response = notificationsApi.getNotifications()

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