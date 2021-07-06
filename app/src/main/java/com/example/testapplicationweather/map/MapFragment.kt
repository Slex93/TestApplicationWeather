package com.example.testapplicationweather.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private var locationPermissionGranted = false
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        setMapView()
        checkPermissionLocation()
        updateLocationUI()

        binding.mapLocation.setOnClickListener {
            updateLocationUI()
        }

        return binding.root
    }

    private fun setMapView(){
        mapView = binding.mapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }

    private fun setCurrentLocation(location: LatLng){
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title("You")
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    private fun checkPermissionLocation() {
        val permissionStatus = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        }
    }

    private fun updateLocationUI() {
        if (locationPermissionGranted) {
            val lm =
                activity?.applicationContext?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            for (provider in lm.allProviders) {
                ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                val location: Location? = lm.getLastKnownLocation(provider)


                val latitude = location?.latitude
                val longitude = location?.longitude

                if (latitude != null && longitude!=null){
                    val currentLocation = LatLng(latitude, longitude)
                    setCurrentLocation(currentLocation)
                }

            }
        } else {
            enableMyLocation()
        }
    }


    private fun enableMyLocation() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        checkPermissionLocation()
        updateLocationUI()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}