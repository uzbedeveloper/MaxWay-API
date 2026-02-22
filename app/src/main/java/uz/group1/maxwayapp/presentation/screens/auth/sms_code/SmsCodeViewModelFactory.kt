package uz.group1.maxwayapp.presentation.screens.auth.sms_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import uz.group1.maxwayapp.app.MyApp
import uz.group1.maxwayapp.data.ApiClient
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.domain.usecase_impl.RegisterUseCaseImpl

class SmsCodeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmsCodeViewModel::class.java)) {
            val authApi = ApiClient.authApi
            val tokenManager = TokenManager(MyApp.instance)
            val repository = AuthRepositoryImpl(authApi, tokenManager, Gson())
            val useCase = RegisterUseCaseImpl(repository)
            
            @Suppress("UNCHECKED_CAST")
            return SmsCodeViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
