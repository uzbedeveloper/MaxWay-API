package uz.group1.maxwayapp.domain.usecase

import uz.group1.maxwayapp.data.model.CategoryUIData

interface CategoriesUseCase {
    suspend operator fun invoke(): Result<List<CategoryUIData>>
}

