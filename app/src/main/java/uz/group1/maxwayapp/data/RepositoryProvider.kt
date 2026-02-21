package uz.group1.maxwayapp.data

import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.domain.repository.ProductRepository

object RepositoryProvider {

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl.getInstance() }

    fun initAll(){
        ProductRepositoryImpl.init()
    }
}