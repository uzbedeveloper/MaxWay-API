package uz.group1.maxwayapp.presentation.screens.auth.phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import uz.group1.maxwayapp.app.MyApp
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.domain.usecase_impl.RegisterUseCaseImpl

class PhoneViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhoneViewModel::class.java)) {
            val authApi = ApiClient.authApi
            val tokenManager = TokenManager(MyApp.instance)
            val repository = AuthRepositoryImpl(authApi, tokenManager, Gson())
            val useCase = RegisterUseCaseImpl(repository)
            
            @Suppress("UNCHECKED_CAST")
            return PhoneViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
