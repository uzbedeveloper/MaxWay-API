package uz.group1.maxwayapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.group1.maxwayapp.data.repository_impl.AddressRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.BranchRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.NotificationsRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl
import uz.group1.maxwayapp.domain.repository.AddressRepository
import uz.group1.maxwayapp.domain.repository.AuthRepository
import uz.group1.maxwayapp.domain.repository.BranchRepository
import uz.group1.maxwayapp.domain.repository.NotificationsRepository
import uz.group1.maxwayapp.domain.repository.ProductRepository
import uz.group1.maxwayapp.domain.repository.StoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule  {

    @[Binds Singleton]
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @[Binds Singleton]
    fun bindAddressRepository(impl: AddressRepositoryImpl): AddressRepository

    @[Binds Singleton]
    fun bindBranchRepository(impl: BranchRepositoryImpl): BranchRepository

    @[Binds Singleton]
    fun bindNotificationsRepository(impl: NotificationsRepositoryImpl): NotificationsRepository

    @[Binds Singleton]
    fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @[Binds Singleton]
    fun bindStoryRepository(impl: StoryRepositoryImpl): StoryRepository


}