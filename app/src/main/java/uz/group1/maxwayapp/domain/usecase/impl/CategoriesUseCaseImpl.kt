package uz.group1.maxwayapp.domain.usecase.impl

import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.CategoriesUseCase

class CategoriesUseCaseImpl(private val repo: ProductRepository): CategoriesUseCase {
    override suspend fun invoke(): Result<List<CategoryUIData>> {
        return repo.getCategories()
    }
}