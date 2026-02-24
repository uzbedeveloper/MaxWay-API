package uz.group1.maxwayapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yandex.mapkit.MapKitFactory
import uz.group1.maxwayapp.data.RepositoryProvider
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.NotificationsRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl
import uz.group1.maxwayapp.data.sources.local.TokenManager
import uz.group1.maxwayapp.domain.repository.NotificationsRepository

class MyApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        TokenManager.init(this)
        MapKitFactory.setApiKey("f65ea3dc-63fd-411e-9043-cce7921a4695")
        MapKitFactory.initialize(this)
    }
}

