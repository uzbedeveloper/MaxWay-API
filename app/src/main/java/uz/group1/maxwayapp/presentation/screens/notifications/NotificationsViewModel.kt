package uz.group1.maxwayapp.presentation.screens.notifications

import androidx.lifecycle.LiveData
import uz.group1.maxwayapp.data.model.NotificationUiData

interface NotificationsViewModel {
    val notificationsLiveData: LiveData<List<NotificationUiData>>
    val errorLiveData: LiveData<String>
    val loadingLiveData: LiveData<Boolean>

    fun loadNotifications()
}

