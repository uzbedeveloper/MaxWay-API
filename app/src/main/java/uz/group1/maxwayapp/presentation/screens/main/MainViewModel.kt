package uz.group1.maxwayapp.presentation.screens.main

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.BannerUIData

interface MainViewModel {
    val banners: StateFlow<List<BannerUIData>>
    fun loadBanners()
}