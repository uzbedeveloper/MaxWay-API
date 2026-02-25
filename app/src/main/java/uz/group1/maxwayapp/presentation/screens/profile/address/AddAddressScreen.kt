package uz.group1.maxwayapp.presentation.screens.profile.address

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.user_location.UserLocationLayer
import kotlinx.coroutines.launch
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.repository_impl.AddressRepositoryImpl
import uz.group1.maxwayapp.databinding.ScreenAddAddressBinding
import uz.group1.maxwayapp.domain.repository.AddressRepository

class AddAddressScreen : Fragment(R.layout.screen_add_address), CameraListener {

    private val binding by viewBinding(ScreenAddAddressBinding::bind)
    private val repository: AddressRepository = AddressRepositoryImpl.getInstance()

    private var currentLatitude = 41.311081
    private var currentLongitude = 69.240562

    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var androidLocationManager: LocationManager
    private var locationListener: LocationListener? = null

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            enableUserLocationAndMoveCamera()
        } else {
            Snackbar.make(binding.root, "Geolokatsiya ruxsati berilmadi", 2000).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        androidLocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        binding.mapview.mapWindow.map.move(
            CameraPosition(Point(currentLatitude, currentLongitude), 14.0f, 0.0f, 0.0f)
        )
        binding.mapview.mapWindow.map.addCameraListener(this)

        userLocationLayer = MapKitFactory.getInstance()
            .createUserLocationLayer(binding.mapview.mapWindow)

        updateCoordinatesText()
        setupZoomButtons()
        setupClickListeners()
        checkAndRequestLocationPermission()
    }

    private fun checkAndRequestLocationPermission() {
        val fine = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        if (fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED) {
            enableUserLocationAndMoveCamera()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableUserLocationAndMoveCamera() {
        userLocationLayer.isVisible = true

        locationListener?.let { androidLocationManager.removeUpdates(it) }
        locationListener = null

        val lastLocation =
            androidLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: androidLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                ?: androidLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

        if (lastLocation != null) {
            moveCameraTo(lastLocation.latitude, lastLocation.longitude)
        }

        val provider = when {
            androidLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
            androidLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
            else -> null
        } ?: return

        val listener = LocationListener { location ->
            locationListener?.let { androidLocationManager.removeUpdates(it) }
            locationListener = null
            if (isAdded) moveCameraTo(location.latitude, location.longitude)
        }
        locationListener = listener
        androidLocationManager.requestLocationUpdates(provider, 0L, 0f, listener, Looper.getMainLooper())
    }

    private fun moveCameraTo(latitude: Double, longitude: Double) {
        binding.mapview.mapWindow.map.move(
            CameraPosition(Point(latitude, longitude), 16f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 1f),
            null
        )
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        currentLatitude = cameraPosition.target.latitude
        currentLongitude = cameraPosition.target.longitude
        updateCoordinatesText()
    }

    private fun updateCoordinatesText() {
        binding.tvCoordinates.text = "%.5f, %.5f".format(currentLatitude, currentLongitude)
    }

    private fun setupZoomButtons() {
        binding.btnZoomIn.setOnClickListener { changeZoom(1.0f) }
        binding.btnZoomOut.setOnClickListener { changeZoom(-1.0f) }
    }

    private fun changeZoom(value: Float) {
        val map = binding.mapview.mapWindow.map
        val pos = map.cameraPosition
        map.move(
            CameraPosition(pos.target, pos.zoom + value, pos.azimuth, pos.tilt),
            Animation(Animation.Type.SMOOTH, 0.3f),
            null
        )
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnMyLocation.setOnClickListener {
            val fine = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            val coarse = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            if (fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED) {
                enableUserLocationAndMoveCamera()
            } else {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etAddressName.text?.toString()?.trim() ?: ""
            if (name.isEmpty()) {
                binding.etAddressName.error = "Nom kiriting"
                return@setOnClickListener
            }
            saveAddress(name)
        }
    }

    private fun saveAddress(name: String) {
        binding.btnSave.isEnabled = false
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.addAddress(name, currentLatitude, currentLongitude)
            result.onSuccess {
                findNavController().navigateUp()
            }.onFailure { error ->
                binding.btnSave.isEnabled = true
                Snackbar.make(binding.root, error.message ?: "Saqlashda xatolik", 2000).show()
            }
        }
    }

    override fun onDestroyView() {
        locationListener?.let { androidLocationManager.removeUpdates(it) }
        locationListener = null
        binding.mapview.mapWindow.map.removeCameraListener(this)
        super.onDestroyView()
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