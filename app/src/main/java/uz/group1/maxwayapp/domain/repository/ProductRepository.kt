package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.model.BannerUIData

interface ProductRepository {
    suspend fun getBanners(): Result<List<BannerUIData>>
}