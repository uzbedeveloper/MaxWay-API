package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.usecase.RepeatUseCase

class RepeatUseCaseImpl(private val repository: AuthRepository) : RepeatUseCase {

    override fun invoke(phone: String): Flow<Result<String>> = flow {
        emit(repository.repeat(phone))
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}