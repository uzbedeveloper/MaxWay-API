package uz.group1.maxwayapp.presentation.screens.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.RegisterUseCaseImpl

class TestViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TestViewModelImpl(RegisterUseCaseImpl(AuthRepositoryImpl.getInstance())) as T
    }
}