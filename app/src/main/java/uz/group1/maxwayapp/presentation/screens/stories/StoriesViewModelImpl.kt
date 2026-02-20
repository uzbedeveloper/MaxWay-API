package uz.group1.maxwayapp.presentation.screens.stories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private var currentJob: Job? = null
    private val _currentStoryIndex = MutableStateFlow(-1)

    private val _navigateToPage = MutableSharedFlow<Int>()
    val navigateToPage = _navigateToPage.asSharedFlow()

    private val storyDuration = 5000L
    private val tickInterval = 50L
    private var isPaused = false

    init {
        loadStories()
    }

    fun setPauseState(pause: Boolean) {
        isPaused = pause
    }

    override fun loadStories() {
        viewModelScope.launch {
            getStoriesUseCase.invoke()
                .onStart { progressLiveData.postValue(true) }
                .onCompletion { progressLiveData.postValue(false) }
                .onEach { result ->
                    result.onSuccess { list ->
                        storiesLiveData.postValue(list)
                        onPageSelected(0)
                    }
                    result.onFailure { errorLiveData.postValue(it.message ?: "Error") }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun startTimerForPage(index: Int) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            var progress = 0
            val totalTicks = (storyDuration / tickInterval).toInt()

            while (progress <= 100) {
                if (!isPaused) {
                    _storyTimerFlow.emit(index to progress)
                    progress += (100 / totalTicks)
                }
                delay(tickInterval)
            }

            val nextIndex = index + 1
            if (nextIndex < (storiesLiveData.value?.size ?: 0)) {
                _navigateToPage.emit(nextIndex)
            }
        }
    }

    fun onPageSelected(index: Int) {
        if (_currentStoryIndex.value == index) return
        _currentStoryIndex.value = index
        startTimerForPage(index)
    }

}