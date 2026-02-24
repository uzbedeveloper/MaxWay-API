package uz.group1.maxwayapp.presentation.screens.profile.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.RegisterUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.RepeatUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.VerifyUseCaseImpl

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = AuthRepositoryImpl.getInstance()
        return RegisterViewModel(RegisterUseCaseImpl(repo), VerifyUseCaseImpl(repo ), RepeatUseCaseImpl(repo)) as T
    }
}