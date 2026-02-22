package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.StoryUIData

interface HomeViewModel {

    val storiesLiveData: LiveData<List<StoryUIData>>
    val errorLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    val banners: StateFlow<List<BannerUIData>>
    val categorys: StateFlow<List<CategoryChipUI>>
    val menu: StateFlow<List<CategoryUIData>>
    fun loadHome()
    fun selectedCategory(categoryId: Int)
    fun loadStories()
}