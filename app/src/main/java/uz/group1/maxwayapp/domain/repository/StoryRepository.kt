package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.model.StoryUIData

interface StoryRepository {
    suspend fun getStories(): Result<List<StoryUIData>>
}