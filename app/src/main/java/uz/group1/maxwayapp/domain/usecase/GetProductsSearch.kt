package uz.group1.maxwayapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.group1.maxwayapp.data.model.ProductSearchUIData

interface GetProductsSearch {
    operator fun invoke(query: String): Flow<Result<List<ProductSearchUIData>>>
}