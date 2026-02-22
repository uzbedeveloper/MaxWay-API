package uz.group1.maxwayapp.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token") val token: String?,
    @SerializedName("message") val message: String?
)