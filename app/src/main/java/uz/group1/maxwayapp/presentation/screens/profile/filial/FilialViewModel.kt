package uz.group1.maxwayapp.presentation.screens.profile.filial

import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.data.model.FilialMapUIData

interface FilialViewModel {
    val branchListFlow: StateFlow<Result<List<FilialListUIData>>?>
    val branchMapFlow: StateFlow<Result<List<FilialMapUIData>>?>

    fun loadBranchList()
    fun loadBranchMap()
}