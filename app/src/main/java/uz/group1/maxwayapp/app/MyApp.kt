package uz.group1.maxwayapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.ProductRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl

class MyApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        AuthRepositoryImpl.init()
        StoryRepositoryImpl.init()
        ProductRepositoryImpl.init()
    }
}

