package uz.group1.maxwayapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.domain.usecase.BannerUseCase
import uz.group1.maxwayapp.domain.usecase.CategoriesWithProductsUseCase

class MainViewModelImpl(
    private val bannerUseCase: BannerUseCase,
    private val categoriesWithProductsUseCase: CategoriesWithProductsUseCase
) : ViewModel(), MainViewModel {

    private val _banners = MutableStateFlow<List<BannerUIData>>(emptyList())
    override val banners: StateFlow<List<BannerUIData>> = _banners
    private val _categories = MutableStateFlow<List<CategoryUIData>>(emptyList())
    override val categories: StateFlow<List<CategoryUIData>> = _categories

    override fun loadBanners() {
        viewModelScope.launch {
            bannerUseCase.getBanners().collect { result ->
                result.onSuccess {
                    _banners.value = it
                }
            }
        }
    }

    override fun loadCategoriesWithProducts() {
        viewModelScope.launch {
            categoriesWithProductsUseCase().onSuccess {
                _categories.value = it
            }
        }
    }
}
