package uz.group1.maxwayapp.presentation.screens.stories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import uz.group1.maxwayapp.data.model.StoryUIData

interface StoriesViewModel {
    val storiesLiveData: LiveData<List<StoryUIData>>
    val errorLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    val storyTimerFlow: StateFlow<Pair<Int, Int>>

    fun loadStories()
}

