package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.model.NotificationUiData

interface NotificationsRepository {
    suspend fun getNotifications(): Result<List<NotificationUiData>>
}