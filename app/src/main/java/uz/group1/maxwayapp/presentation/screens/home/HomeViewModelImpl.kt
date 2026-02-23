package uz.group1.maxwayapp.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
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

        if (homeScreenElements.value.banners.isNotEmpty()) return

        viewModelScope.launch {
            homeScreenElements.update { it.copy(isLoading = true) }

            val bannersDef = async { bannerUseCase.getAllBanners().first() }
            val categoriesDef = async { categorysUseCase().first() }
            val menuDef = async { menuProductUseCase().first() }
            val storiesDef = async { getStories().first() }

            homeScreenElements.update {
                it.copy(
                    isLoading = false,
                    banners = bannersDef.await().getOrNull() ?: emptyList(),
                    categories = categoriesDef.await().getOrNull() ?: emptyList(),
                    menu = menuDef.await().getOrNull() ?: emptyList(),
                    stories = storiesDef.await().getOrNull() ?: emptyList()
                )
            }

            menuProductUseCase().collect { result ->
                result.onSuccess { updatedMenu ->
                    Log.d("TTT", "loadHome: $updatedMenu")
                    homeScreenElements.update { it.copy(menu = updatedMenu, isLoading = false) }
                }
                result.onFailure {
                    Log.d("TTT", "loadHome: $it")
                    homeScreenElements.update { it.copy(isLoading = false) }
                }
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