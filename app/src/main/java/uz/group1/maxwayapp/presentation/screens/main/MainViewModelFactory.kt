package uz.group1.maxwayapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.BannerUseCaseImpl

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModelImpl(BannerUseCaseImpl(ProductRepositoryImpl.getInstance())) as T
    }
}