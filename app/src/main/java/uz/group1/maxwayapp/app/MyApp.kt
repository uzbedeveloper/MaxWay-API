package uz.group1.maxwayapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import uz.group1.maxwayapp.data.RepositoryProvider
import uz.group1.maxwayapp.data.repository_impl.AuthRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.NotificationsRepositoryImpl
import uz.group1.maxwayapp.data.repository_impl.StoryRepositoryImpl
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

        AuthRepositoryImpl.init()
        StoryRepositoryImpl.init()


        RepositoryProvider.initAll()
        NotificationsRepositoryImpl.init()
    }
}

