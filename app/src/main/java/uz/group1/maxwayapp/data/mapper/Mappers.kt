package uz.group1.maxwayapp.data.mapper

import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.data.model.NotificationUiData
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.response.BannerResponse
import uz.group1.maxwayapp.data.sources.remote.response.NotificationData
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