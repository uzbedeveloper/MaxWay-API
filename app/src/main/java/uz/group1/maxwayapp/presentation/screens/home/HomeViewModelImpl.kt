package uz.group1.maxwayapp.presentation.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.domain.HomeUiElements
import uz.group1.maxwayapp.domain.usecase.BannerUseCase
import uz.group1.maxwayapp.domain.usecase.GetCategoriesUseCase
import uz.group1.maxwayapp.domain.usecase.GetMenuUseCase
import uz.group1.maxwayapp.domain.usecase.GetStoriesUseCase


class HomeViewModelImpl(
    private val bannerUseCase: BannerUseCase,
    private val categorysUseCase: GetCategoriesUseCase,
    private val menuProductUseCase: GetMenuUseCase,
    private val getStories: GetStoriesUseCase
) : ViewModel(), HomeViewModel{

    override val uiState = MutableStateFlow(HomeUiElements())

    override fun loadHome() {
        viewModelScope.launch {
            uiState.update { it.copy(isLoading = true) }

            val bannersDef = async { bannerUseCase.getAllBanners().first() }
            val categoriesDef = async { categorysUseCase().first() }
            val menuDef = async { menuProductUseCase().first() }
            val storiesDef = async { getStories().first() }

            uiState.update {
                it.copy(
                    isLoading = false,
                    banners = bannersDef.await().getOrNull() ?: emptyList(),
                    categories = categoriesDef.await().getOrNull() ?: emptyList(),
                    menu = menuDef.await().getOrNull() ?: emptyList(),
                    stories = storiesDef.await().getOrNull() ?: emptyList()
                )
            }
        }
    }

    override fun selectedCategory(categoryId: Int) {
        uiState.update { currentState ->
            val updatedCategories = currentState.categories.map { chip ->
                chip.copy(isSelected = chip.id == categoryId)
            }
            currentState.copy(categories = updatedCategories)
        }
    }
}