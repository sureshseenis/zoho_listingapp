package com.zoho.listingapp.activities.listing

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.location.*
import com.zoho.listingapp.BuildConfig
import com.zoho.listingapp.R
import com.zoho.listingapp.adapters.CountriesListAdapter
import com.zoho.listingapp.anim.SpacesItemDecoration
import com.zoho.listingapp.databinding.ActivityListingBinding
import com.zoho.listingapp.di.coponent.DaggerWeatherComponent
import com.zoho.listingapp.di.factory.ViewModelFactory
import com.zoho.listingapp.di.factory.WeatherViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ListingActivity : AppCompatActivity() {
    lateinit var listingBinding: ActivityListingBinding
    lateinit var listingViewModel: ListingActivityViewModel
    lateinit var listingViewModelWeather: ListingActivityViewModel

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listingBinding = DataBindingUtil.setContentView(this, R.layout.activity_listing)

        //ViewModel provider
        val viewModelFactory = ViewModelFactory()
        val viewModelWeatherFactory = WeatherViewModelFactory()
        listingViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListingActivityViewModel::class.java)
        listingViewModelWeather=ViewModelProvider(this, viewModelWeatherFactory).get(ListingActivityViewModel::class.java)

        listingViewModel.getCountriesFromApi()


        //when get countries response
        listingViewModel.restCountries.observe(this, Observer {
            listingBinding.rvCountries.adapter = CountriesListAdapter(this, it)
            listingBinding.rvCountries.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            listingBinding.rvCountries.addItemDecoration(SpacesItemDecoration(16))

            if (checkPermissionForLocation(this)) {
                startLocationUpdates()
            }

        })

        //When get the weather response
        listingViewModel.weatherReaponse.observe(this, Observer {
             listingBinding.tvCity.text=it.location.name
            listingBinding.tvCondition.text=""+it.current.condition.text
        })


        mLocationRequest = LocationRequest()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    private fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11
                )
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()


    }


    protected fun startLocationUpdates() {

        // Create the location request to start receiving updates

        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined

        mLastLocation = location
        /*val date: Date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("hh:mm:ss a")*/
        Log.d("ListingActivity",""+mLastLocation.latitude+""+mLastLocation.longitude)
        listingViewModelWeather.getWeatherDetails(""+mLastLocation.latitude+","+mLastLocation.longitude)
        stoplocationUpdates()
    }

    private fun stoplocationUpdates() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
             /*   btnStartupdate.isEnabled = false
                btnStopUpdates.isEnabled = true*/
            } else {
                goToSettings()

            }
        }
    }

    fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }

    private fun goToSettings() {
        val myAppSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        myAppSettings.data = uri
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivityForResult(myAppSettings, REQUEST_PERMISSION_LOCATION)
        Toast.makeText(this, "Go to Permissions and grant permissions.", Toast.LENGTH_LONG).show()
    }

}