package uz.group1.maxwayapp.presentation.screens.stories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.StoryUIData
import uz.group1.maxwayapp.domain.usecase.GetStoriesUseCase

class StoriesViewModelImpl(
    private val getStoriesUseCase: GetStoriesUseCase
): ViewModel(),StoriesViewModel {
    override val storiesLiveData = MutableLiveData<List<StoryUIData>>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()

    private val _storyTimerFlow = MutableStateFlow(0 to 0)
    override val storyTimerFlow = _storyTimerFlow.asStateFlow()

    private var timerJob: Job? = null
    private val storyDuration = 5000L
    private val tickInterval = 50L

    init {
        loadStories()
    }

    override fun loadStories() {
        viewModelScope.launch {
            getStoriesUseCase.invoke()
                .onStart { progressLiveData.postValue(true) }
                .onCompletion { progressLiveData.postValue(false) }
                .onEach { result ->
                    result.onSuccess { list ->
                        storiesLiveData.postValue(list)
                        startTimer(list.size)
                    }
                    result.onFailure { errorLiveData.postValue(it.message ?: "Error") }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun startTimer(count: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (index in 0 until count) {
                var progress = 0
                while (progress <= 100) {
                    _storyTimerFlow.emit(index to progress)
                    delay(tickInterval)
                    progress += (100 / (storyDuration / tickInterval)).toInt()
                }
            }
        }
    }

}