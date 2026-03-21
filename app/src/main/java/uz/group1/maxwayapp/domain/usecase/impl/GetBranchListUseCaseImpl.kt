package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.domain.repository.BranchRepository
import uz.group1.maxwayapp.domain.usecase.GetBranchListUseCase
import javax.inject.Inject

class GetBranchListUseCaseImpl @Inject constructor(private val repo: BranchRepository): GetBranchListUseCase {
    override fun getBranchLists(): Flow<Result<List<FilialListUIData>>> = flow{
        emit(repo.getBranchList())
    }
}