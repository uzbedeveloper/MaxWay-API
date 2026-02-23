package uz.group1.maxwayapp.presentation.screens.profile.filial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.group1.maxwayapp.data.repository_impl.BranchRepositoryImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetBranchListUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetBranchMapDataImpl

@Suppress("UNCHECKED_CAST")
class FilialViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = BranchRepositoryImpl.getInstance()
        return FilialViewModelImpl(
            getBranchListUseCase = GetBranchListUseCaseImpl(repo),
            getBranchMapData = GetBranchMapDataImpl(repo)
        ) as T
    }
}