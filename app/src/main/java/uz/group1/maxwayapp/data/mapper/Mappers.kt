package uz.group1.maxwayapp.data.mapper

import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.data.model.CategoryChipUI
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.data.model.FilialMapUIData
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.data.model.ProductSearchUIData
import uz.group1.maxwayapp.data.model.ProductUIData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.model.UserUIData
import uz.group1.maxwayapp.data.sources.remote.response.BannerResponse
import uz.group1.maxwayapp.data.sources.remote.response.NotificationData
import uz.group1.maxwayapp.data.sources.remote.response.CategoryResponse
import uz.group1.maxwayapp.data.sources.remote.response.FilialData
import uz.group1.maxwayapp.data.sources.remote.response.ProductResponse
import uz.group1.maxwayapp.data.sources.remote.response.StoryData
import uz.group1.maxwayapp.data.sources.remote.response.UserDataResponse
import uz.group1.maxwayapp.data.sources.remote.response.order.myOrders.Data
import uz.group1.maxwayapp.data.sources.remote.response.order.myOrders.ProductItem

fun StoryData.toUiData(): StoryUIData {

    return StoryUIData(id, url, name)
}

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
        products = product.map { it.toUIData() },
        count = 0
    )
}

fun CategoryResponse.toCategoryUIData(): CategoryUIData{
    return CategoryUIData(
        id = id,
        name = name,
        products = emptyList(),
        count = 0
    )
}
fun CategoryUIData.toChipUI(isSelected: Boolean = false): CategoryChipUI {
    return CategoryChipUI(
        id = id,
        name = name,
        isSelected = isSelected
    )
}
fun FilialData.toDetailsUIdata(): FilialListUIData{
    return FilialListUIData(
        id = id,
        name = name,
        address = address,
        phone = phone,
        openTime = openTime,
        closeTime = closeTime
    )
}
fun FilialData.toMapUiData(): FilialMapUIData {
    return FilialMapUIData(
        id = id,
        latitude = latitude,
        longitude = longitude
    )
}
fun ProductResponse.toUISData(): ProductSearchUIData{
    return ProductSearchUIData(
        id = id,
        categoryID = categoryID,
        name = name,
        description = description,
        image = image,
        cost= cost

    )
}

fun UserDataResponse.toUserUI(): UserUIData{
    return UserUIData(
        id = id,
        name = name,
        phone = phone,
        birthDate = birthDate
    )
}

fun ProductItem.toProductUiData(): ProductUIData{
    return ProductUIData(productData.id, productData.categoryID, productData.name, productData.description, productData.image, productData.cost, count)
}

fun Data.toUIData(): MyOrdersUIData {
    return MyOrdersUIData(
        id = id,
        address = address,
        createTime = createTime,
        latitude = latitude,
        longitude = longitude,
        ls = ls,
        sum = sum,
        userID = userID,
        currentStage = 1,
        orderNumber = "100",
        statusText = "Yaratildi"
    )
}