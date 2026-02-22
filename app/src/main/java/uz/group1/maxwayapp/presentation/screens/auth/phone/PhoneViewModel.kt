package uz.group1.maxwayapp.presentation.screens.auth.phone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.domain.usecase.RegisterUseCase

class PhoneViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "PhoneViewModel"
    }

    private val _uiState = MutableStateFlow<PhoneUiState>(PhoneUiState.Idle)
    val uiState: StateFlow<PhoneUiState> = _uiState

    fun sendPhone(phone: String) {
        viewModelScope.launch {
            Log.d(TAG, "sendPhone() boshlandi → $phone")
            _uiState.value = PhoneUiState.Loading
            registerUseCase.sendPhone(phone).collectLatest { result ->
                result.fold(
                    onSuccess = { response ->
                        // SMS kodini logga chiqaramiz
                        Log.d(TAG, "sendPhone() muvaffaqiyatli! SMS kodi: ${response.code}")
                        _uiState.value = PhoneUiState.Success
                    },
                    onFailure = {
                        Log.e(TAG, "sendPhone() xatolik → ${it.message}")
                        _uiState.value = PhoneUiState.Error(it.message ?: "Xatolik yuz berdi")
                    }
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = PhoneUiState.Idle
    }
}

sealed class PhoneUiState {
    object Idle : PhoneUiState()
    object Loading : PhoneUiState()
    object Success : PhoneUiState()
    data class Error(val message: String) : PhoneUiState()
}
