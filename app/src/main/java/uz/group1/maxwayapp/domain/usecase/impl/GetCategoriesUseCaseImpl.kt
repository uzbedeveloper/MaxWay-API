package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.data.mapper.toChipUI
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.GetCategoriesUseCase

class GetCategoriesUseCaseImpl(private val repo: ProductRepository): GetCategoriesUseCase {
    override fun invoke(): Flow<Result<List<CategoryChipUI>>> = flow{
        val result= repo.getCategories()
        val chipResul = result.map { list ->
            list.mapIndexed { index, category ->
                category.toChipUI(isSelected = index == 0)
            }
        }
        emit(chipResul)
    }.flowOn(Dispatchers.IO)
}