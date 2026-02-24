package uz.group1.maxwayapp.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.group1.maxwayapp.data.model.CategoryUIData
import uz.group1.maxwayapp.data.model.MyOrdersUIData
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.usecase.GetMyOrdersUseCase

class GetMyOrdersUseCaseImpl(private val repository: ProductRepository): GetMyOrdersUseCase {
    override fun invoke(): Flow<Result<List<MyOrdersUIData>>> = flow{
        val initial = repository.getMyOrders()

        if (initial.isSuccess){
            emit(initial)
        }else{
            emit(Result.failure(initial.exceptionOrNull() ?: Exception("Unknown Error")))
        }
    }
}
