package uz.group1.maxwayapp.data.sources.remote.response

data class UserDataResponse(
    val id: Int,
    val name: String?,
    val phone: String?,
    val birthDate: String?,
    val token: String?
)