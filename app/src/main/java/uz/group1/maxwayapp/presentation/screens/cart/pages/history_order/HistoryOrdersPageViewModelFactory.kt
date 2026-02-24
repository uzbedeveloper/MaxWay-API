package uz.group1.maxwayapp.presentation.screens.cart.pages.history_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetMyOrdersUseCaseImpl

class HistoryOrdersPageViewModelFactory: ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryOrdersPageViewModel(GetMyOrdersUseCaseImpl(ProductRepositoryImpl.getInstance())) as T
    }
}