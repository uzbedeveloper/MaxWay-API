package uz.group1.maxwayapp.data.sources.local

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "user_info"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_NAME = "user_name"
    private const val KEY_PHONE = "user_phone"
    private const val KEY_BIRTH_DATE = "user_birth_date"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(token: String?, name: String?, phone: String?, birthDate: String?) {
        prefs.edit().apply {
            token?.let { putString(KEY_TOKEN, it) }
            name?.let { putString(KEY_NAME, it) }
            phone?.let { putString(KEY_PHONE, it) }
            birthDate?.let { putString(KEY_BIRTH_DATE, it) }
            apply()
        }
    }

    fun getToken(): String = prefs.getString(KEY_TOKEN, "")?:""
    fun getName(): String? = prefs.getString(KEY_NAME, "")
    fun getPhone(): String? = prefs.getString(KEY_PHONE, "")
    fun getBirthDate(): String? = prefs.getString(KEY_BIRTH_DATE, "")

    fun clear() {
        prefs.edit().clear().apply()
    }
}