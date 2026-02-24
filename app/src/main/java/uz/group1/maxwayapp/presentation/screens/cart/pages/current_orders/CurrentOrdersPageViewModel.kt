package uz.group1.maxwayapp.presentation.screens.cart.pages.current_orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.domain.usecase.GetMyOrdersUseCase

class CurrentOrdersPageViewModel(
    private val getMyOrdersUseCase: GetMyOrdersUseCase
): ViewModel(), CurrentOrdersPageContract {
    override val ordersLiveData = MutableLiveData<List<MyOrdersUIData>>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()

    init {
        loadMyOrders()
    }

    override fun loadMyOrders() {
        getMyOrdersUseCase.invoke()
            .onStart { progressLiveData.postValue(true) }
            .onCompletion { progressLiveData.postValue(false) }
            .onEach {
                it.onSuccess { list ->
                    ordersLiveData.postValue(list)
                }
                it.onFailure { error ->
                    errorLiveData.postValue(error.message ?: "Unknown Error")
                }
            }.launchIn(viewModelScope)
    }

}