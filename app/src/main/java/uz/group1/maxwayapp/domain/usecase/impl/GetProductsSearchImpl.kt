package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.group1.maxwayapp.data.model.ProductSearchUIData
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.GetProductsSearch

class GetProductsSearchImpl(private val repo: ProductRepository): GetProductsSearch {
    override fun invoke(query: String): Flow<Result<List<ProductSearchUIData>>> = flow{
        val res = repo.getSearchProducts(query)
        emit(res)
    }

}