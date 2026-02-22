package uz.group1.maxwayapp.presentation.screens.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetStoriesUseCaseImpl

class StoriesViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repo = StoryRepositoryImpl.getInstance()
        val getStoriesUseCase = GetStoriesUseCaseImpl(repo)

        return StoriesViewModelImpl(getStoriesUseCase) as T
    }
}