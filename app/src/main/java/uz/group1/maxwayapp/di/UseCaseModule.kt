package uz.group1.maxwayapp.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.group1.maxwayapp.domain.usecase.BannerUseCase
import uz.group1.maxwayapp.domain.usecase.GetBranchListUseCase
import uz.group1.maxwayapp.domain.usecase.GetBranchMapData
import uz.group1.maxwayapp.domain.usecase.GetCategoriesUseCase
import uz.group1.maxwayapp.domain.usecase.GetMenuUseCase
import uz.group1.maxwayapp.domain.usecase.GetMyOrdersUseCase
import uz.group1.maxwayapp.domain.usecase.GetNotificationsUseCase
import uz.group1.maxwayapp.domain.usecase.GetProductsSearch
import uz.group1.maxwayapp.domain.usecase.GetStoriesUseCase
import uz.group1.maxwayapp.domain.usecase.RegisterUseCase
import uz.group1.maxwayapp.domain.usecase.RepeatUseCase
import uz.group1.maxwayapp.domain.usecase.UpdateProductCountUseCase
import uz.group1.maxwayapp.domain.usecase.VerifyUseCase
import uz.group1.maxwayapp.domain.usecase.impl.BannerUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetBranchListUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetBranchMapDataImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetCategoriesUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetMenuUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetMyOrdersUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetNotificationsUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetProductsSearchImpl
import uz.group1.maxwayapp.domain.usecase.impl.GetStoriesUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.RegisterUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.RepeatUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.UpdateProductCountUseCaseImpl
import uz.group1.maxwayapp.domain.usecase.impl.VerifyUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindBannerUseCase(impl: BannerUseCaseImpl): BannerUseCase

    @Binds
    fun bindGetBranchListUseCase(impl: GetBranchListUseCaseImpl): GetBranchListUseCase

    @Binds
    fun bindGetBranchMapData(impl: GetBranchMapDataImpl): GetBranchMapData

    @Binds
    fun bindGetCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    fun bindGetMenuUseCase(impl: GetMenuUseCaseImpl): GetMenuUseCase

    @Binds
    fun bindGetMyOrdersUseCase(impl: GetMyOrdersUseCaseImpl): GetMyOrdersUseCase

    @Binds
    fun bindGetNotificationsUseCase(impl: GetNotificationsUseCaseImpl): GetNotificationsUseCase

    @Binds
    fun bindGetProductsSearch(impl: GetProductsSearchImpl): GetProductsSearch

    @Binds
    fun bindGetStoriesUseCase(impl: GetStoriesUseCaseImpl): GetStoriesUseCase

    @Binds
    fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    fun bindRepeatUseCase(impl: RepeatUseCaseImpl): RepeatUseCase

    @Binds
    fun bindUpdateProductCountUseCase(impl: UpdateProductCountUseCaseImpl): UpdateProductCountUseCase

    @Binds
    fun bindVerifyUseCase(impl: VerifyUseCaseImpl): VerifyUseCase
}