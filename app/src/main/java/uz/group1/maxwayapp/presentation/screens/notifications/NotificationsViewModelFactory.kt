package uz.group1.maxwayapp.presentation.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.NotificationsRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetNotificationsUseCase

class NotificationsViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repo = NotificationsRepositoryImpl.getInstance()
        val getNotificationsUseCase = GetNotificationsUseCase(repo)

        return NotificationsViewModelImpl(getNotificationsUseCase) as T
    }
}