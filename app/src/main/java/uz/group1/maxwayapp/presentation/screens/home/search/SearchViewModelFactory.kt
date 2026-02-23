package uz.group1.maxwayapp.presentation.screens.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetProductsSearchImpl

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(GetProductsSearchImpl(ProductRepositoryImpl.getInstance())) as T
    }
}