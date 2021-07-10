package com.example.testapplicationweather.map

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testapplicationweather.R
import com.example.testapplicationweather.databinding.FragmentMapBinding
import com.example.testapplicationweather.databinding.HeadWeatherBinding
import com.example.testapplicationweather.map.model.MapRepository
import com.example.testapplicationweather.map.viewmodel.MapViewModel
import com.example.testapplicationweather.map.viewmodel.MapViewModelFactory
import com.example.testapplicationweather.utilites.convertToCelsius
import com.example.testapplicationweather.utilites.getCoordinates
import com.example.testapplicationweather.utilites.getWeatherTitle
import com.example.testapplicationweather.utilites.setIcon
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private var markerLocation: Marker? = null
    private var markerCurrentWeather: Marker? = null

    private val repository = MapRepository()
    private val viewModel: MapViewModel by viewModels {
        MapViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
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
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    updateUI()

                } else {
                    mapClicker()
                    return
                }
            }
            else -> {
            }
        }
    }

    private fun updateUI() {
        setLocation()
        binding.mapLocation.setOnClickListener {
            setLocation()
        }
        mapClicker()
    }

    private fun mapClicker() {
        map.setOnMapClickListener {
            if (markerCurrentWeather != null) markerCurrentWeather?.remove()
            markerCurrentWeather = map.addMarker(
                MarkerOptions()
                    .position(it)
                    .title(getString(R.string.weather))
            )
            showDialogInformation(it.getCoordinates())
        }
        map.setOnInfoWindowClickListener {
            showDialogInformation(it.position.getCoordinates())
        }
    }

    private fun showDialogInformation(coordinates: String) {
        viewModel.initRetrofit(coordinates)
        val bindingHead = HeadWeatherBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = Dialog(requireContext())
        dialog.setContentView(bindingHead.root)
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        bindingHead.headerProgressBar.visibility = View.VISIBLE
        bindingHead.headerCloseIcon.setOnClickListener { dialog.cancel() }
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            bindingHead.headerProgressBar.visibility = View.GONE
            bindingHead.headerCloseIcon.visibility = View.VISIBLE
            bindingHead.mainFragmentIcon.setIcon(it.currently.icon)
            bindingHead.mainFragmentTemperature.text = it.currently.temperature.convertToCelsius()
            bindingHead.mainFragmentWeather.text = getWeatherTitle(it.currently.summary)
        }
        dialog.show()
        dialog.setOnCancelListener {
            Snackbar.make(binding.root, "CANCELED", Snackbar.LENGTH_SHORT).show()
            bindingHead.headerCloseIcon.visibility = View.GONE
            bindingHead.headerProgressBar.visibility = View.GONE
            viewModel.currentWeather.removeObservers(viewLifecycleOwner)
        }
    }

    private fun setLocation() {
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
        if (markerLocation != null) markerLocation?.remove()
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

