package uz.group1.maxwayapp.presentation.screens.auth.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.sources.local.LocalStorage

class NameViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NameViewModel(LocalStorage.instance) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
