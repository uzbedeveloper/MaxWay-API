package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.usecase.VerifyUseCase

class VerifyUseCaseImpl(private val repository: AuthRepository) : VerifyUseCase {

    override fun invoke(phone: String, code: Int): Flow<Result<Boolean>> = flow {
        emit(repository.verify(phone, code))
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}