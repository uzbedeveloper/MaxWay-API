package uz.group1.maxwayapp.presentation.screens.profile.filial.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.FilialMapUIData
import uz.group1.maxwayapp.databinding.PageMapBinding
import uz.group1.maxwayapp.presentation.screens.base_fragment.BaseFragment
import uz.group1.maxwayapp.presentation.screens.profile.filial.FilialViewModel
import uz.group1.maxwayapp.presentation.screens.profile.filial.FilialViewModelFactory
import uz.group1.maxwayapp.presentation.screens.profile.filial.FilialViewModelImpl

class MapScreen : Fragment(R.layout.page_map) {
    private val binding by viewBinding(PageMapBinding::bind)

    private val viewModel: FilialViewModel by viewModels<FilialViewModelImpl> { FilialViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapview.mapWindow.map.move(
            CameraPosition(Point(41.311081, 69.240562), 12.0f, 0.0f, 0.0f)
        )

        setupZoomButtons()
        observeViewModel()

        viewModel.loadBranchMap()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.branchMapFlow.collect { result ->
                result?.onSuccess { mapItems ->
                    addMarkers(mapItems)
                }
            }
        }
    }

    private fun addMarkers(items: List<FilialMapUIData>) {
        val mapObjects = binding.mapview.mapWindow.map.mapObjects
        mapObjects.clear()

        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.marker)
        val iconStyle = com.yandex.mapkit.map.IconStyle().apply {
            scale = 0.3f
            anchor = android.graphics.PointF(0.5f, 0.5f)
        }
        items.forEach { filial ->
            val point = Point(filial.latitude, filial.longitude)
            val placemark = mapObjects.addPlacemark(point)

            placemark.setIcon(imageProvider, iconStyle)

            placemark.addTapListener { _, _ ->
                binding.mapview.mapWindow.map.move(
                    CameraPosition(point, 15.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 0.5f),
                    null
                )
                true
            }
        }
    }

    private fun setupZoomButtons() {
        binding.btnZoomIn.setOnClickListener { changeZoom(1.0f) }
        binding.btnZoomOut.setOnClickListener { changeZoom(-1.0f) }
    }

    private fun changeZoom(value: Float) {
        val map = binding.mapview.mapWindow.map
        val currentPosition = map.cameraPosition
        val newPosition = CameraPosition(
            currentPosition.target,
            currentPosition.zoom + value,
            currentPosition.azimuth,
            currentPosition.tilt
        )
        map.move(newPosition, Animation(Animation.Type.SMOOTH, 0.3f), null)
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