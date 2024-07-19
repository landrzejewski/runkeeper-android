package pl.training.runkeeper.tracking.adapters.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.os.Looper.getMainLooper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import pl.training.runkeeper.R
import pl.training.runkeeper.databinding.FragmentTrackingBinding

class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding
    private lateinit var map: GoogleMap
    private lateinit var locationClient: FusedLocationProviderClient
    private val locationRequest = LocationRequest.Builder(1_000)
        .setPriority(PRIORITY_HIGH_ACCURACY)
        .setMinUpdateDistanceMeters(5F)
        .build()
    private val viewModel: TrackingViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTrackingBinding.inflate(layoutInflater)
        locationClient = getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReady)
    }

    private val onMapReady = OnMapReadyCallback {
        map = it
        requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            configureMap()
            locationClient.requestLocationUpdates(locationRequest, onLocationUpdate, getMainLooper())
        } else {
            Toast.makeText(requireContext(), "Permission request denied", LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun configureMap() {
        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        locationClient.lastLocation.addOnSuccessListener(::moveCamera)
    }

    private fun moveCamera(location: Location) {
        val position = LatLng(location.latitude, location.longitude)
        val positionUpdate = newLatLngZoom(position, CAMERA_ZOOM)
        map.animateCamera(positionUpdate)
    }

    private val onLocationUpdate = object : LocationCallback() {

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.lastLocation?.let {
                moveCamera(it)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        locationClient.removeLocationUpdates(onLocationUpdate)
    }

    companion object {

        const val CAMERA_ZOOM = 16F

    }


}