package uz.group1.maxwayapp.data.model

import java.io.Serializable

data class StoryUIData(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val durationMs: Long = 30000L
): Serializable