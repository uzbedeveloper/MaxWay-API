package uz.group1.maxwayapp.presentation.screens.profile.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.domain.usecase.RegisterUseCase
import uz.group1.maxwayapp.domain.usecase.RepeatUseCase
import uz.group1.maxwayapp.domain.usecase.VerifyUseCase

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val verifyUseCase: VerifyUseCase,
    private val repeatUseCase: RepeatUseCase
): ViewModel() {

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()
    private val _registerSuccess = MutableSharedFlow<String>()
    val registerSuccess = _registerSuccess.asSharedFlow()
    private val _verifySuccess = MutableSharedFlow<Boolean>()
    val verifySuccess = _verifySuccess.asSharedFlow()
    private val _timerFlow = MutableStateFlow(0)
    val timerFlow: StateFlow<Int> = _timerFlow
    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow: StateFlow<Boolean> = _loadingFlow


    fun register(phone: String) {
        viewModelScope.launch {
            _loadingFlow.value = true
            registerUseCase(phone).collect { result ->
                _loadingFlow.value = false
                result.onSuccess { _registerSuccess.emit(phone) }
                    .onFailure { _errorFlow.emit(it.message ?: "Xatolik") }
            }
        }
    }

    fun verify(phone: String, code: Int) {
        viewModelScope.launch {
            _loadingFlow.value = true
            verifyUseCase(phone, code).collect { result ->
                _loadingFlow.value = false
                result.onSuccess { hasProfile -> _verifySuccess.emit(hasProfile) }
                    .onFailure { _errorFlow.emit(it.message ?: "Kod xato") }
            }
        }
    }

    fun startTimer() {
        viewModelScope.launch {
            for (i in 59 downTo 0) {
                _timerFlow.value = i
                delay(1000)
            }
            _timerFlow.value = 0
        }
    }

    fun resendCode(phone: String) {
        viewModelScope.launch {
            repeatUseCase(phone).collect { result ->
                result.onSuccess {
                    _registerSuccess.emit(phone)
                    startTimer()
                }.onFailure { _errorFlow.emit(it.message ?: "Xatolik") }
            }
        }
    }
}