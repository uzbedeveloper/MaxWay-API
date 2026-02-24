package uz.group1.maxwayapp.data.sources.remote.request

data class AddressEditRequest(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)