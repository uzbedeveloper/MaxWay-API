package uz.group1.maxwayapp.domain.repository

import uz.group1.maxwayapp.data.model.FilialListUIData
import uz.group1.maxwayapp.data.model.FilialMapUIData

interface BranchRepository {
    suspend fun getBranchList(): Result<List<FilialListUIData>>
    suspend fun getBranchMapData(): Result<List<FilialMapUIData>>
}
