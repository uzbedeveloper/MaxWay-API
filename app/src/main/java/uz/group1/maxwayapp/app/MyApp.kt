package uz.group1.maxwayapp.app

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("f65ea3dc-63fd-411e-9043-cce7921a4695")
        MapKitFactory.initialize(this)
    }
}

