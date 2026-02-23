package uz.group1.maxwayapp.data.sources.remote.response

data class FilialData(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openTime: String,
    val closeTime: String,
    val latitude: Double,
    val longitude: Double
)