package uz.group1.maxwayapp.app

import android.app.Application
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.sources.local.LocalStorage

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Repositoriy va LocalStorage'ni ishga tushirish
        AuthRepositoryImpl.init()
        LocalStorage.init(this)
    }
}
