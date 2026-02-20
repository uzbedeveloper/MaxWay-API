package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.domain.repository.StoryRepository
import uz.group1.maxwayapp.domain.usecase.GetStoriesUseCase

class GetStoriesUseCase(private val repository: StoryRepository) : GetStoriesUseCase {
    override suspend operator fun invoke(): Flow<Result<List<StoryUIData>>> = flow{
        emit(repository.getStories())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

}