package uz.group1.maxwayapp.data

import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.NotificationsRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.repository.NotificationsRepository
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.repository.StoryRepository
import kotlin.getValue

@Deprecated("Har bir repo ni o'zida getInstance ni yozib ketdim, bu kerak emas endi")
object RepositoryProvider {

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl.getInstance() }
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl.getInstance() }
    val storyRepository: StoryRepository by lazy { StoryRepositoryImpl.getInstance() }
    val notificationsRepository: NotificationsRepository by lazy { NotificationsRepositoryImpl.getInstance() }

}