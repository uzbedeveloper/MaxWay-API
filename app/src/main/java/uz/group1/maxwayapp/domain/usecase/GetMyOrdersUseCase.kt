package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.MyOrdersUIData

interface GetMyOrdersUseCase {
    operator fun invoke(): Flow<Result<List<MyOrdersUIData>>>
}