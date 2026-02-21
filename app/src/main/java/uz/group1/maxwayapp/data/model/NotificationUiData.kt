package uz.group1.maxwayapp.data.model

data class NotificationUiData(
    val id: Int,
    val imgURL: String,
    val message: String,
    val name: String,
    val sendDate: String,
    val isRead: Boolean = false
)