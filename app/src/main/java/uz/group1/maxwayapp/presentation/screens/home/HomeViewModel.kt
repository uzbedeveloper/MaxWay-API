package uz.group1.maxwayapp.presentation.screens.home

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.domain.models.HomeUiElements

interface HomeViewModel {

    val homeScreenElements:StateFlow<HomeUiElements>

    fun loadHome()
    fun selectedCategory(categoryId: Int)
    fun updateProductCount(productId: Int, newCount: Int)
}