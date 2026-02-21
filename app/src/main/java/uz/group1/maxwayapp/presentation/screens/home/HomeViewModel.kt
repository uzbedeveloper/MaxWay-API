package uz.group1.maxwayapp.presentation.screens.home

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.BannerUIData

interface HomeViewModel {
    val banners: StateFlow<List<BannerUIData>>
    fun loadBanners()
}