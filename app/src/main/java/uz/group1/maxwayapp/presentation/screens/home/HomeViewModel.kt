package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.domain.HomeUiElements

interface HomeViewModel {

    val uiState:StateFlow<HomeUiElements>

    fun loadHome()
    fun selectedCategory(categoryId: Int)
}