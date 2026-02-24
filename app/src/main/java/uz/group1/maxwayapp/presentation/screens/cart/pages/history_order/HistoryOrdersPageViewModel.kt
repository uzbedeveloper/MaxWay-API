package uz.group1.maxwayapp.presentation.screens.cart.pages.history_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.domain.usecase.GetMyOrdersUseCase

class HistoryOrdersPageViewModel(
    private val getMyOrdersUseCase: GetMyOrdersUseCase
): ViewModel(), HistoryOrdersPageContract {
    override val ordersLiveData = MutableLiveData<List<MyOrdersUIData>>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()

    override fun loadMyOrders() {
        getMyOrdersUseCase.invoke()
            .onStart { progressLiveData.postValue(true) }
            .onCompletion { progressLiveData.postValue(false) }
            .onEach {
                it.onSuccess { list ->
                    val currentTime = System.currentTimeMillis()

                    val activeOrders = list.filter { order ->
                        val diffInMs = currentTime - order.createTime
                        val minutesPassed = (diffInMs / (1000 * 60)).toInt()

                        minutesPassed >= 20
                    }

                    ordersLiveData.postValue(activeOrders)
                }
                it.onFailure { error ->
                    errorLiveData.postValue(error.message ?: "Unknown Error")
                }
            }.launchIn(viewModelScope)
    }

}