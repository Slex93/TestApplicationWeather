package com.example.testapplicationweather.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

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
        val location = LatLng(55.753913, 37.620836)
        val zoom = 30
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ), zoom.toFloat()
            )
        )
    }

}