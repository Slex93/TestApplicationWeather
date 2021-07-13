package com.example.testapplicationweather.map

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testapplicationweather.R
import com.example.testapplicationweather.databinding.FragmentMainBinding
import com.example.testapplicationweather.databinding.FragmentMapBinding
import com.example.testapplicationweather.databinding.HeadWeatherBinding
import com.example.testapplicationweather.map.model.MapRepository
import com.example.testapplicationweather.map.viewmodel.MapViewModel
import com.example.testapplicationweather.map.viewmodel.MapViewModelFactory
import com.example.testapplicationweather.utilites.convertToCelsius
import com.example.testapplicationweather.utilites.getCoordinates
import com.example.testapplicationweather.utilites.getWeatherTitle
import com.example.testapplicationweather.utilites.setIcon
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.concurrent.Executor

class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var _bindingDialog: HeadWeatherBinding? = null
    private val bindingDialog get() = _bindingDialog!!

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private var markerLocation: Marker? = null
    private var markerCurrentWeather: Marker? = null
    private lateinit var locationManager: LocationManager

    private val repository = MapRepository()
    private val viewModel: MapViewModel by viewModels {
        MapViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
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

        _bindingDialog = HeadWeatherBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = Dialog(requireContext())
        dialog.setContentView(bindingDialog.root)
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        bindingDialog.headerProgressBar.visibility = View.VISIBLE
        bindingDialog.headerCloseIcon.setOnClickListener { dialog.cancel() }
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            bindingDialog.headerProgressBar.visibility = View.GONE
            bindingDialog.headerCloseIcon.visibility = View.VISIBLE
            bindingDialog.mainFragmentIcon.setIcon(it.currently.icon)
            bindingDialog.mainFragmentTemperature.text = it.currently.temperature.convertToCelsius()
            bindingDialog.mainFragmentWeather.text = getWeatherTitle(it.currently.summary)
        }
        dialog.show()
        dialog.setOnCancelListener {
            bindingDialog.headerCloseIcon.visibility = View.GONE
            bindingDialog.headerProgressBar.visibility = View.GONE
            viewModel.currentWeather.removeObservers(viewLifecycleOwner)
            _bindingDialog = null
        }
    }

    private fun setLocation() {
        binding.mapProgress.visibility = View.VISIBLE
        locationManager =
            requireActivity().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val time = Calendar.getInstance().timeInMillis - 2 * 60 * 1000
        if (location != null && location.time >= time) {
            val latitude = location.latitude
            val longtitude = location.longitude
            setFocus(LatLng(latitude, longtitude))
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10f, this)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10f, this)
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
        binding.mapProgress.visibility = View.GONE
    }

    override fun onLocationChanged(location: Location) {
        setFocus(LatLng(location.latitude, location.longitude))
        locationManager.removeUpdates(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE = 1
    }

}

