package uz.group1.maxwayapp.data.sources.remote.request

import com.google.gson.annotations.SerializedName

//data class RegisterRequest(
//    @SerializedName("phone") val phone: String
//)

data class VerifyRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: Int
)

data class RepeatSmsRequest(
    @SerializedName("phone") val phone: String
)