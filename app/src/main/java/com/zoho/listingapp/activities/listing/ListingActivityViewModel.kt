package com.zoho.listingapp.activities.listing

import android.util.Log
import androidx.lifecycle.*
import com.zoho.listingapp.api.models.RestCountries
import com.zoho.listingapp.api.models.WeatherResponse
import com.zoho.listingapp.api.services.ApiService
import com.zoho.listingapp.api.services.WeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class ListingActivityViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : ViewModel() {

    var restCountries = MutableLiveData<List<RestCountries.RestCountriesItem>>()
    var weatherReaponse = MutableLiveData<WeatherResponse>()

    //Data get from api
    fun getCountriesFromApi() {
        //use coroutines
        viewModelScope.launch(dispatcher) {
            try {
                val responseCountries = apiService.getRestCountries()
                if (responseCountries.isSuccessful) {
                    restCountries.value = responseCountries.body()
                }else Log.d("Error","Api error")

            } catch (e: Exception) {
                Log.d("Error","Api error"+e.message)
            }

        }

    }

    fun getWeatherDetails(strLatLong: String) {
        //user coroutines
        viewModelScope.launch(dispatcher) {
            try {

                val weatherApiResponse =apiService.getWeatherDetails(
                    "e966819dbf17430390f65612200811",
                    strLatLong
                )

                if (weatherApiResponse.isSuccessful) {
                    weatherReaponse.value = weatherApiResponse.body()
                }else Log.d("Error","Api error")


            } catch (e: Exception) {
                Log.d("Error","Api error"+e.message)
            }

        }
    }

}