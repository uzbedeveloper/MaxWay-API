package uz.group1.maxwayapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.domain.usecase.BannerUseCase

class HomeViewModelImpl(private val bannerUseCase: BannerUseCase) : ViewModel(), HomeViewModel{
    private val _banners = MutableStateFlow<List<BannerUIData>>(emptyList())
    override val banners: StateFlow<List<BannerUIData>> = _banners

    override fun loadBanners() {
        viewModelScope.launch {
            bannerUseCase.getAllBanners().collect { result ->
                result.onSuccess{
                    _banners.value = it
                }
            }
        }
    }
}