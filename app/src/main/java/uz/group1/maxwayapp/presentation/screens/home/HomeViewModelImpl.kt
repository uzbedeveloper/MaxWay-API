package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.domain.usecase.BannerUseCase
import uz.group1.maxwayapp.domain.usecase.GetCategoriesUseCase

class HomeViewModelImpl(private val bannerUseCase: BannerUseCase, private val categorysUseCase: GetCategoriesUseCase) : ViewModel(), HomeViewModel{
    private val _banners = MutableStateFlow<List<BannerUIData>>(emptyList())
    override val banners: StateFlow<List<BannerUIData>> = _banners

    private val _categorys = MutableStateFlow<List<CategoryChipUI>>(emptyList())
    override val categorys: StateFlow<List<CategoryChipUI>> = _categorys


    override fun loadHome() {
        loadBanners()
        loadCategories()
    }

    override fun selectedCategory(categoryId: Int) {
        _categorys.update { curList->
            curList.map { chip ->
                chip.copy(isSelected = chip.id == categoryId)
            }
        }
    }


    private fun loadCategories(){
        viewModelScope.launch {
            categorysUseCase().collect { result->
                result.onSuccess { list ->
                    _categorys.value = list
                }
            }
        }
    }
    private fun loadBanners() {
        viewModelScope.launch {
            bannerUseCase.getAllBanners().collect { result ->
                result.onSuccess{
                    _banners.value = it
                }
            }
        }
    }
}