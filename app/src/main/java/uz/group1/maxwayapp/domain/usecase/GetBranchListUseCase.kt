package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.FilialListUIData

interface GetBranchListUseCase {
    fun getBranchLists(): Flow<Result<List<FilialListUIData>>>
}