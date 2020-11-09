package com.zoho.listingapp.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoho.listingapp.activities.listing.WeatherViewModel
import com.zoho.listingapp.api.services.ApiService
import com.zoho.listingapp.di.coponent.DaggerWeatherComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofitWeather: Retrofit

    lateinit var apiService: ApiService

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        DaggerWeatherComponent.builder().build().inject(this)
        apiService= retrofitWeather.create(ApiService::class.java)

        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                Dispatchers.Main,
                apiService
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}