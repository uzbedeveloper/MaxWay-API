package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.CategoryUIData

interface GetMenuUseCase {
    operator fun invoke(): Flow<Result<List<CategoryUIData>>>
}