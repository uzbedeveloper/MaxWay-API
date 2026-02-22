package uz.group1.maxwayapp.data.sources.local

import android.content.Context
import android.content.SharedPreferences

class LocalStorage private constructor(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("maxway_prefs", Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var INSTANCE: LocalStorage? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = LocalStorage(context)
                    }
                }
            }
        }

        val instance: LocalStorage
            get() = INSTANCE ?: throw IllegalStateException("LocalStorage not initialized")
    }

    var userName: String?
        get() = preferences.getString("user_name", null)
        set(value) = preferences.edit().putString("user_name", value).apply()
}
