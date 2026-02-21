package uz.group1.maxwayapp.presentation.screens.home

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryChipUI

interface HomeViewModel {
    val banners: StateFlow<List<BannerUIData>>
    val categorys: StateFlow<List<CategoryChipUI>>
    fun loadHome()
    fun selectedCategory(categoryId: Int)
}