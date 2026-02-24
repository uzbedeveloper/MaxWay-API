package uz.group1.maxwayapp.data.sources.remote.request

data class VerifyRequest(
    val phone: String,
    val code: Int
)
