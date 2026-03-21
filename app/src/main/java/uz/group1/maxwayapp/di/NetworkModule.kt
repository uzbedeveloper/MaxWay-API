package uz.group1.maxwayapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.group1.maxwayapp.BuildConfig.BASE_URL
import uz.group1.maxwayapp.data.sources.remote.api.AddressApi
import uz.group1.maxwayapp.data.sources.remote.api.AuthApi
import uz.group1.maxwayapp.data.sources.remote.api.BranchApi
import uz.group1.maxwayapp.data.sources.remote.api.NotificationsApi
import uz.group1.maxwayapp.data.sources.remote.api.ProductApi
import uz.group1.maxwayapp.data.sources.remote.api.StoriesApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class )
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { HttpLoggingInterceptor.Level.BODY })
        .addInterceptor(ChuckerInterceptor.Builder(context = context).build())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson( )

    @[Provides Singleton]
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun provideStorieApi(retrofit: Retrofit): StoriesApi = retrofit.create(StoriesApi::class.java)

    @[Provides Singleton]
    fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

    @[Provides Singleton]
    fun provideNotificationsApi(retrofit: Retrofit): NotificationsApi = retrofit.create(NotificationsApi::class.java)

    @[Provides Singleton]
    fun provideBranchApi(retrofit: Retrofit): BranchApi = retrofit.create(BranchApi::class.java)

    @[Provides Singleton]
    fun provideAddressApi(retrofit: Retrofit): AddressApi = retrofit.create(AddressApi::class.java)

}

