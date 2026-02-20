package uz.group1.maxwayapp.data.model

data class StoryUIData(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val durationMs: Long = 30000L
)