package uz.group1.maxwayapp.presentation.screens.home

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.domain.HomeUiElements

interface HomeViewModel {

    val uiState:StateFlow<HomeUiElements>

    fun loadHome()
    fun selectedCategory(categoryId: Int)
}