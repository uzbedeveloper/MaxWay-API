package uz.group1.maxwayapp.domain.usecase

interface UpdateProductCountUseCase {
    operator fun invoke(productId: Int, newCount: Int)
}