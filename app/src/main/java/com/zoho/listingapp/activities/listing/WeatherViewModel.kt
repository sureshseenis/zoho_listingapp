package com.zoho.listingapp.activities.listing


import android.util.Log
import androidx.lifecycle.*
import com.zoho.listingapp.api.models.WeatherResponse
import com.zoho.listingapp.api.services.ApiService
import kotlinx.coroutines.*
import java.lang.Exception

class WeatherViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : ViewModel() {

    var weatherReaponse = MutableLiveData<WeatherResponse>()

    fun getWeatherDetails(strLatLong: String) {

        //user coroutines
        viewModelScope.launch(dispatcher) {
            try {
                val weatherApiResponse =apiService.getWeatherDetails(
                    "e966819dbf17430390f65612200811",
                    strLatLong
                )
                Log.d("Error", "getWeatherDetails: "+strLatLong+" Code "+weatherApiResponse.code() + weatherApiResponse.raw().networkResponse());
                if (weatherApiResponse.isSuccessful) {
                    weatherReaponse.value = weatherApiResponse.body()
                }else Log.d("Error","Api error")


            } catch (e: Exception) {
                Log.d("Error","Api error"+e.message)
            }

        }
    }

}