package uz.group1.maxwayapp.presentation.screens.profile.filial

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yandex.mapkit.MapKitFactory
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ItemMapBinding

class MapScreen : Fragment(R.layout.item_map) {
    private val binding by viewBinding(ItemMapBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapview.mapWindow.map.move(
            com.yandex.mapkit.map.CameraPosition(
                com.yandex.mapkit.geometry.Point(41.311081, 69.240562),
                12.0f, 0.0f, 0.0f
            )
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}