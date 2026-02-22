package uz.group1.maxwayapp.presentation.screens.auth.name

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.sources.local.LocalStorage

class NameViewModel(
    private val localStorage: LocalStorage
) : ViewModel() {

    companion object {
        private const val TAG = "NameViewModel"
    }

    private val _uiState = MutableStateFlow<NameUiState>(NameUiState.Idle)
    val uiState: StateFlow<NameUiState> = _uiState

    fun saveName(name: String) {
        viewModelScope.launch {
            Log.d(TAG, "saveName() → name: $name")
            localStorage.userName = name
            Log.d(TAG, "Ism saqlandi, Success holatiga o'tilmoqda")
            _uiState.value = NameUiState.Success
        }
    }

    fun resetState() {
        _uiState.value = NameUiState.Idle
    }
}

sealed class NameUiState {
    object Idle : NameUiState()
    object Success : NameUiState()
}
