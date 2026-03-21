package uz.group1.maxwayapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import uz.group1.maxwayapp.domain.usecase.GetMenuUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getMenuUseCase: GetMenuUseCase) : ViewModel() {

    val totalCartCount = getMenuUseCase().map { result ->
        result.getOrNull()?.sumOf { category ->
            category.products.sumOf { it.count }
        } ?: 0
    }
}