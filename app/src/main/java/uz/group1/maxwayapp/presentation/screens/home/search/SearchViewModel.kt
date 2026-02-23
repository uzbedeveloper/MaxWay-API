package uz.group1.maxwayapp.presentation.screens.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.ProductSearchUIData
import uz.group1.maxwayapp.domain.usecase.GetProductsSearch

class SearchViewModel(private val getSearchPrUseCase: GetProductsSearch) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductSearchUIData>>(emptyList())
    val products: StateFlow<List<ProductSearchUIData>> = _products.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()

        if (query.isEmpty()) {
            _products.value = emptyList()
            _isLoading.value = false
            return
        }

        searchJob = viewModelScope.launch {
            delay(500L)
            _isLoading.value = true

            getSearchPrUseCase(query).onEach { result ->
                _isLoading.value = false
                result.onSuccess { list ->
                    _products.value = list
                }.onFailure {
                    _products.value = emptyList()
                }
            }.launchIn(viewModelScope)
        }
    }
}