package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.GetMenuUseCase

class GetMenuUseCaseImpl(private val repo: ProductRepository) : GetMenuUseCase {
    override fun invoke(): Flow<Result<List<CategoryUIData>>> = flow {
        val initial = repo.getCategoriesWithProducts()
        if (initial.isSuccess) {
            emit(initial)
        }

        repo.getMenu().collect { updatedList ->
            emit(Result.success(updatedList))
        }
    }
}