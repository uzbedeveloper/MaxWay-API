package uz.group1.maxwayapp.data.sources.remote.response

data class GeneralResponse<T>(
    val message: String,
    val data: T
)
