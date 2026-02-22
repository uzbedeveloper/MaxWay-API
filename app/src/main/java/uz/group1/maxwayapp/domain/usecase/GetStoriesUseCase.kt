package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.StoryUIData

interface GetStoriesUseCase {
    operator fun invoke(): Flow<Result<List<StoryUIData>>>
}

