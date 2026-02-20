package uz.group1.maxwayapp.presentation.screens.main

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData

interface MainViewModel {
    val banners: StateFlow<List<BannerUIData>>
    val categories: StateFlow<List<CategoryUIData>>
    fun loadBanners()
    fun loadCategoriesWithProducts()
}