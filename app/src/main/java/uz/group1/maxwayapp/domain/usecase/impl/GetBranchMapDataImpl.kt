package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.group1.maxwayapp.data.model.FilialMapUIData
import uz.group1.maxwayapp.domain.repository.BranchRepository
import uz.group1.maxwayapp.domain.usecase.GetBranchMapData

class GetBranchMapDataImpl(private val repo: BranchRepository): GetBranchMapData {
    override fun getBranchMaps(): Flow<Result<List<FilialMapUIData>>> = flow{
        emit(repo.getBranchMapData())
    }
}