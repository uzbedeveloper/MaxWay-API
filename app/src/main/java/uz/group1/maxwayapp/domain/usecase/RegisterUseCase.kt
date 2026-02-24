package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    operator fun invoke(phone: String) : Flow<Result<String>>
}