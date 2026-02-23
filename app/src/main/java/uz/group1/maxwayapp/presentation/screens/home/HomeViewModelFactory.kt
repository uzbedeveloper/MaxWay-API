package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.RepositoryProvider
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.BannerUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetCategoriesUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetMenuUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetStoriesUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.UpdateProductCountUseCaseImpl

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = RepositoryProvider.productRepository
        return HomeViewModelImpl(
           bannerUseCase =  BannerUseCaseImpl(repo),
            categorysUseCase = GetCategoriesUseCaseImpl(repo),
            menuProductUseCase = GetMenuUseCaseImpl(repo),
            updateProductCountUseCase = UpdateProductCountUseCaseImpl(repo),
            getStories = GetStoriesUseCaseImpl(StoryRepositoryImpl.getInstance())
        ) as T
    }
}