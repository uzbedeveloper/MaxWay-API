package uz.group1.maxwayapp.presentation.screens.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.group1.maxwayapp.domain.usecase.RegisterUseCase

class TestViewModelImpl (private val registerUseCase: RegisterUseCase) : ViewModel(), TestViewModel {
    override val loadingLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun register(phone: String) {
        registerUseCase(phone)
            .onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }
            .onEach { result ->
                result.onSuccess {
                    successLiveData.value = Unit
                }
                result.onFailure {
                    errorMessageLiveData.value = it.message ?: "Unknown exception"
                }
            }
            .launchIn(viewModelScope)
    }

}


