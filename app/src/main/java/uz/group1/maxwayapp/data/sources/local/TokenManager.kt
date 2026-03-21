package uz.group1.maxwayapp.data.sources.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context){
    private val PREF_NAME = "user_info"
    private val KEY_TOKEN = "auth_token"
    private val KEY_NAME = "user_name"
    private val KEY_PHONE = "user_phone"
    private val KEY_BIRTH_DATE = "user_birth_date"

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)



    fun saveUserData(token: String?, name: String?, phone: String?, birthDate: String?) {
        prefs.edit().apply {
            token?.let { putString(KEY_TOKEN, it) }
            name?.let { putString(KEY_NAME, it) }
            phone?.let { putString(KEY_PHONE, it) }
            birthDate?.let { putString(KEY_BIRTH_DATE, it) }
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)?.takeIf { it.isNotEmpty() }
    fun getName(): String? = prefs.getString(KEY_NAME, "")
    fun getPhone(): String? = prefs.getString(KEY_PHONE, "")
    fun getBirthDate(): String? = prefs.getString(KEY_BIRTH_DATE, "")

    fun clear() {
        prefs.edit().clear().apply()
    }
}