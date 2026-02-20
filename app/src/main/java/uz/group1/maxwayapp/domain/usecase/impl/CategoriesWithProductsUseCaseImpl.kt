package uz.group1.maxwayapp.domain.usecase.impl

import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.CategoriesWithProductsUseCase


class CategoriesWithProductsUseCaseImpl(private val repo: ProductRepository) : CategoriesWithProductsUseCase {

    override suspend fun invoke(): Result<List<CategoryUIData>> {
        return repo.getCategoriesWithProducts()
    }
}