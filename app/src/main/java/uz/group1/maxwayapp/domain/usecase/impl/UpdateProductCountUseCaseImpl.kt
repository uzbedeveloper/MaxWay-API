package uz.group1.maxwayapp.domain.usecase.impl

import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.UpdateProductCountUseCase
import javax.inject.Inject

class UpdateProductCountUseCaseImpl @Inject constructor(private val repository: ProductRepository): UpdateProductCountUseCase {
   override operator fun invoke(productId: Int, newCount: Int) {
        repository.updateProductCount(productId, newCount)
    }
}