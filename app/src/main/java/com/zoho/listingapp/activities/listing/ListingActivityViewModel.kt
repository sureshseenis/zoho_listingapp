package com.zoho.listingapp.activities.listing

import androidx.lifecycle.*
import com.zoho.listingapp.api.models.RestCountries
import com.zoho.listingapp.api.services.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class ListingActivityViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : ViewModel(), LifecycleObserver {

     var restCountries = MutableLiveData<List<RestCountries.RestCountriesItem>>()

    //Data get from api
    fun getCountriesFromApi() {
        //use coroutines
        viewModelScope.launch(dispatcher) {
            try {
                val responseCountries = apiService.getRestCountries()
                if (responseCountries.isSuccessful) {
                    restCountries.value = responseCountries.body()
                }

            } catch (e: Exception) {

            }

        }

    }

}