package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.FilialMapUIData

interface GetBranchMapData {
    fun getBranchMaps(): Flow<Result<List<FilialMapUIData>>>
}