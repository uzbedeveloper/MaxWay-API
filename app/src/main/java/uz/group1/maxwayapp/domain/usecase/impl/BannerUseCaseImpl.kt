package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.BannerUseCase

class BannerUseCaseImpl(private val repo: ProductRepository): BannerUseCase {
    override fun getAllBanners(): Flow<Result<List<BannerUIData>>> = flow{
        emit(repo.getBanners())
    }

}