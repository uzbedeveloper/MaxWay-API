package uz.group1.maxwayapp.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.domain.models.HomeUiElements
import uz.group1.maxwayapp.domain.usecase.BannerUseCase
import uz.group1.maxwayapp.domain.usecase.GetCategoriesUseCase
import uz.group1.maxwayapp.domain.usecase.GetMenuUseCase
import uz.group1.maxwayapp.domain.usecase.GetStoriesUseCase
import uz.group1.maxwayapp.domain.usecase.impl.UpdateProductCountUseCaseImpl


class HomeViewModelImpl(
    private val bannerUseCase: BannerUseCase,
    private val categorysUseCase: GetCategoriesUseCase,
    private val menuProductUseCase: GetMenuUseCase,
    private val updateProductCountUseCase: UpdateProductCountUseCaseImpl,
    private val getStories: GetStoriesUseCase
) : ViewModel(), HomeViewModel{

    override val homeScreenElements = MutableStateFlow(HomeUiElements())

    init {
        loadHome()
    }

    override fun loadHome() {

        homeScreenElements.update { it.copy(isLoading = true, isError = false) }

        viewModelScope.launch {
            delay(1200)
            if (homeScreenElements.value.banners.isNotEmpty()) return@launch

            homeScreenElements.update { it.copy(isLoading = true, isError = false) }

            val menuJob = launch {
                menuProductUseCase().collect { result ->
                    result.onSuccess { updatedMenu ->
                        homeScreenElements.update {
                            it.copy(menu = updatedMenu, isLoading = false, isError = false)
                        }
                    }
                    result.onFailure {
                        homeScreenElements.update {
                            it.copy(isLoading = false, isError = true)
                        }
                    }
                }
            }
            val bannersDef = async { bannerUseCase.getAllBanners().first() }
            val categoriesDef = async { categorysUseCase().first() }
            val storiesDef = async { getStories().first() }

            homeScreenElements.update {
                it.copy(
                    isLoading = false,
                    banners = bannersDef.await().getOrNull() ?: emptyList(),
                    categories = categoriesDef.await().getOrNull() ?: emptyList(),
                    stories = storiesDef.await().getOrNull() ?: emptyList()
                )
            }
        }
    }

    override fun selectedCategory(categoryId: Int) {
        homeScreenElements.update { currentState ->
            val updatedCategories = currentState.categories.map { chip ->
                chip.copy(isSelected = chip.id == categoryId)
            }
            currentState.copy(categories = updatedCategories)
        }
    }
    override fun updateProductCount(productId: Int, newCount: Int) {
        updateProductCountUseCase(productId, newCount)
        Log.d("TTT", "updateProductCount: $productId, count = $newCount")
    }
}