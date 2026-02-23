package uz.group1.maxwayapp.domain.models

import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.StoryUIData

data class HomeUiElements(
    val isLoading: Boolean = true,
    val banners: List<BannerUIData> = emptyList(),
    val categories: List<CategoryChipUI> = emptyList(),
    val menu: List<CategoryUIData> = emptyList(),
    val stories: List<StoryUIData> = emptyList()
)