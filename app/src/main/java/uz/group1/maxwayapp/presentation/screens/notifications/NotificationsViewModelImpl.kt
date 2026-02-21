package uz.group1.maxwayapp.presentation.screens.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.domain.usecase.GetNotificationsUseCase

class NotificationsViewModelImpl(
    private val getNotifications: GetNotificationsUseCase
): ViewModel(),NotificationsViewModel {

    override val notificationsLiveData = MutableLiveData<List<NotificationUiData>>()
    override val errorLiveData = MutableLiveData<String>()
    override val loadingLiveData = MutableLiveData<Boolean>()

    init {
        loadNotifications()
    }

    override fun loadNotifications() {
        getNotifications()
            .onStart { loadingLiveData.postValue(true) }
            .onCompletion { loadingLiveData.postValue(false) }
            .onEach { result ->
                result.onSuccess { list ->
                    notificationsLiveData.postValue(list)
                }
                result.onFailure { errorLiveData.postValue(it.message ?: "Error") }
            }
            .launchIn(viewModelScope)
    }
}