package uz.group1.maxwayapp.data.mapper

import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.response.BannerResponse
import uz.group1.maxwayapp.data.sources.remote.response.NotificationData
import uz.group1.maxwayapp.data.sources.remote.response.CategoriesResponse
import uz.group1.maxwayapp.data.sources.remote.response.CategoryResponse
import uz.group1.maxwayapp.data.sources.remote.response.ProductResponse
import uz.group1.maxwayapp.data.sources.remote.response.StoryData

fun StoryData.toUiData(): StoryUIData{

    return StoryUIData(id, url,name)

fun NotificationData.toUiData(): NotificationUiData{
    return NotificationUiData(id, imgURL, message, name, sendDate)
}

fun BannerResponse.toBannerUIData(): BannerUIData{
    return BannerUIData(
        id = id,
        bannerUrl = bannerUrl
    )
}

fun ProductResponse.toUIData(): ProductUIData{
    return ProductUIData(
        id = id,
        categoryID = categoryID,
        name = name,
        description = description,
        image = image,
        cost= cost,
        count = 0
    )
}
fun CategoryResponse.toUIData(product: List<ProductResponse>): CategoryUIData{
    return CategoryUIData(
        id = id,
        name = name,
        products = product.map { it.toUIData() }
    )
}

fun CategoryResponse.toCategoryUIData(): CategoryUIData{
    return CategoryUIData(
        id = id,
        name = name,
        products = emptyList()
    )
}
fun CategoryUIData.toChipUI(isSelected: Boolean = false): CategoryChipUI {
    return CategoryChipUI(
        id = id,
        name = name,
        isSelected = isSelected
    )
}