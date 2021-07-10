package com.example.testapplicationweather.map

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private var markerLocation: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        setMapView()
        return binding.root
    }

    private fun setMapView() {
        mapView = binding.mapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        checkPermissionOfFragment()
    }

    private fun checkPermissionOfFragment() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                updateUI()
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.i("Permission:", "request")

        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.i("Permission:", "apply")
                    updateUI()

                } else {
                    Log.i("Permission:", "deny")
                    return
                }
            }
            else -> {
                Log.i("Permission:", "ignore")
            }
        }
    }

    private fun updateUI() {
        Log.i("Permission:", "updateUI")
        setLocation()
        binding.mapLocation.setOnClickListener {
            Log.i("Permission:", "click")
            updateUI()
        }
    }

    private fun setLocation(){
        val lm =
            requireActivity().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (l != null) {
            val latitude = l.latitude
            val longtitude = l.longitude
            val location = LatLng(latitude, longtitude)
            setFocus(location)
        }
    }

    private fun setFocus(location: LatLng) {
        val zoom = 15
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ), zoom.toFloat()
            )
        )
        if(markerLocation != null) markerLocation?.remove()
        markerLocation = map.addMarker(
            MarkerOptions()
                .position(location)
                .title("you")
        )
    }

    companion object {
        const val REQUEST_CODE = 1
    }

}

