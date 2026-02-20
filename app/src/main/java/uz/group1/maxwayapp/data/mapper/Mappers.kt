package uz.group1.maxwayapp.data.mapper

import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.data.sources.remote.response.StoryData

fun StoryData.toUiData(): StoryUIData{

    return StoryUIData(id, url,name)

}