package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.RepositoryProvider
import uz.group1.maxwayapp.domain.usecase.impl.BannerUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetCategoriesUseCaseImpl

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = RepositoryProvider.productRepository
        return HomeViewModelImpl(BannerUseCaseImpl(repo), GetCategoriesUseCaseImpl(repo = repo)) as T
    }
}