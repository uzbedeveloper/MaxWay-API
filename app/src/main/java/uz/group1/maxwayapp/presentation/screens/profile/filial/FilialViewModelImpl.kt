package uz.group1.maxwayapp.presentation.screens.profile.filial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.data.model.FilialMapUIData
import uz.group1.maxwayapp.domain.usecase.GetBranchListUseCase
import uz.group1.maxwayapp.domain.usecase.GetBranchMapData

class FilialViewModelImpl(
    private val getBranchListUseCase: GetBranchListUseCase,
    private val getBranchMapData: GetBranchMapData
) : ViewModel(), FilialViewModel {

    override val branchListFlow = MutableStateFlow<Result<List<FilialListUIData>>?>(null)
    override val branchMapFlow = MutableStateFlow<Result<List<FilialMapUIData>>?>(null)

    override fun loadBranchList() {
        getBranchListUseCase.getBranchLists()
            .onEach { branchListFlow.value = it }
            .launchIn(viewModelScope)
    }

    override fun loadBranchMap() {
        getBranchMapData.getBranchMaps()
            .onEach { branchMapFlow.value = it }
            .launchIn(viewModelScope)
    }
}