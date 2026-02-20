package uz.group1.maxwayapp.data

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.group1.maxwayapp.BuildConfig.BASE_URL
import uz.group1.maxwayapp.app.MyApp
import uz.group1.maxwayapp.data.sources.remote.api.AuthApi
import uz.group1.maxwayapp.data.sources.remote.api.StoriesApi

object ApiClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor.Builder(MyApp.instance).build())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
    val storiesApi = retrofit.create(StoriesApi::class.java)
}