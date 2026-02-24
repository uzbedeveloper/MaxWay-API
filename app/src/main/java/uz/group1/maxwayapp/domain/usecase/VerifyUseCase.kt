package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow

interface VerifyUseCase {
    operator fun invoke(phone: String, code: Int): Flow<Result<Boolean>>
}