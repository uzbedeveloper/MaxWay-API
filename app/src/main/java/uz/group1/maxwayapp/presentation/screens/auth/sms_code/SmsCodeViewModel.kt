package uz.group1.maxwayapp.presentation.screens.auth.sms_code

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.domain.usecase.RegisterUseCase

class SmsCodeViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SmsCodeViewModel"
    }

    private val _uiState = MutableStateFlow<SmsCodeUiState>(SmsCodeUiState.Idle)
    val uiState: StateFlow<SmsCodeUiState> = _uiState

    private val _timerText = MutableStateFlow("00:59")
    val timerText: StateFlow<String> = _timerText

    private val _canResend = MutableStateFlow(false)
    val canResend: StateFlow<Boolean> = _canResend

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    fun verifyCode(phone: String, code: Int) {
        viewModelScope.launch {
            _uiState.value = SmsCodeUiState.Loading
            registerUseCase.verifyCode(phone, code).collectLatest { result ->
                result.fold(
                    onSuccess = {
                        _uiState.value = SmsCodeUiState.Success
                    },
                    onFailure = {
                        _uiState.value = SmsCodeUiState.Error(it.message ?: "Xatolik yuz berdi")
                    }
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = SmsCodeUiState.Idle
    }

    fun resendCode(phone: String) {
        viewModelScope.launch {
            registerUseCase.resendCode(phone).collectLatest { result ->
                result.fold(
                    onSuccess = {
                        _canResend.value = false
                        startTimer()
                    },
                    onFailure = {
                        _uiState.value = SmsCodeUiState.Error(it.message ?: "Xatolik yuz berdi")
                    }
                )
            }
        }
    }

    private fun startTimer(seconds: Int = 59) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (i in seconds downTo 0) {
                val mins = i / 60
                val secs = i % 60
                _timerText.value = "%02d:%02d".format(mins, secs)
                delay(1000)
            }
            _canResend.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

sealed class SmsCodeUiState {
    object Idle : SmsCodeUiState()
    object Loading : SmsCodeUiState()
    object Success : SmsCodeUiState()
    data class Error(val message: String) : SmsCodeUiState()
}
