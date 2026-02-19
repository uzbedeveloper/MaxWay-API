package uz.group1.maxwayapp.presentation.screens.test

import androidx.lifecycle.LiveData

interface TestViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<Unit>
    val errorMessageLiveData: LiveData<String>

    fun register(phone: String)
}

