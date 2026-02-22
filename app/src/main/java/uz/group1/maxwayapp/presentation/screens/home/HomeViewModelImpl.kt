package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _banners = MutableStateFlow<List<BannerUIData>>(emptyList())
    override val banners: StateFlow<List<BannerUIData>> = _banners

    private val _categorys = MutableStateFlow<List<CategoryChipUI>>(emptyList())
    override val categorys: StateFlow<List<CategoryChipUI>> = _categorys

    private val _menu = MutableStateFlow<List<CategoryUIData>>(emptyList())
    override val menu: StateFlow<List<CategoryUIData>> = _menu

    override val storiesLiveData = MutableLiveData<List<StoryUIData>>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()

    override fun loadHome() {
        loadBanners()
        loadCategories()
        loadMenu()
        loadStories()
    }

    override fun selectedCategory(categoryId: Int) {
        _categorys.update { curList->
            curList.map { chip ->
                chip.copy(isSelected = chip.id == categoryId)
            }
        }
    }

    private fun loadMenu(){
        viewModelScope.launch {
            menuProductUseCase().collect { result ->
                result.onSuccess { list->
                    _menu.value = list
                }
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

    override fun loadStories() {
        getStories()
            .onStart { progressLiveData.postValue(true) }
            .onCompletion { progressLiveData.postValue(false) }
            .onEach { result ->
                result.onSuccess { list ->
                    storiesLiveData.postValue(list)
                }
                result.onFailure { errorLiveData.postValue(it.message ?: "Error") }
            }
            .launchIn(viewModelScope)
    }
}