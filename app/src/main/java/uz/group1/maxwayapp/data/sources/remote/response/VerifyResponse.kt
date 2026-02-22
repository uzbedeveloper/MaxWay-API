package uz.group1.maxwayapp.data.sources.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyResponse(
    @SerializedName("token") val token: String
)