package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.domain.repository.NotificationsRepository
import uz.group1.maxwayapp.domain.usecase.GetNotificationsUseCase

class GetNotificationsUseCase(private val repository: NotificationsRepository) : GetNotificationsUseCase {
    override operator fun invoke(): Flow<Result<List<NotificationUiData>>> = flow{
        emit(repository.getNotifications())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

}