package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.BannerUIData

interface BannerUseCase {
    fun getAllBanners(): Flow<Result<List<BannerUIData>>>
}