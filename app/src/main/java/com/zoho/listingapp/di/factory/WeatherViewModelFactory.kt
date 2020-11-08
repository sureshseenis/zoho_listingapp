package com.zoho.listingapp.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zoho.listingapp.activities.listing.ListingActivityViewModel
import com.zoho.listingapp.api.services.ApiService
import com.zoho.listingapp.api.services.WeatherService
import com.zoho.listingapp.di.coponent.DaggerWeatherComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofitWeather: Retrofit

    lateinit var apiService: ApiService

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        DaggerWeatherComponent.create().inject(this)

        apiService= retrofitWeather.create(ApiService::class.java)

        if (modelClass.isAssignableFrom(ListingActivityViewModel::class.java)) {
            return ListingActivityViewModel(
                Dispatchers.Main,
                apiService
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}