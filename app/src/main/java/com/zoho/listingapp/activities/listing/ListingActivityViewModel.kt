package com.zoho.listingapp.activities.listing

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.zoho.listingapp.api.models.RestCountries
import com.zoho.listingapp.api.services.ApiService
import com.zoho.listingapp.room.Countries
import com.zoho.listingapp.room.DatabaseHelper
import kotlinx.coroutines.*
import java.lang.Exception

class ListingActivityViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : ViewModel() {

    var restCountries = MutableLiveData<List<RestCountries.RestCountriesItem>>()
    var countiesDbData = MutableLiveData<List<Countries>>()
    lateinit var dbHelper: DatabaseHelper

    var progress=MutableLiveData<Boolean>(true)
    var progressPercent=ObservableField<Int>(10)

    //Data get from api
    fun getCountriesFromApi() {
        //use coroutines
        viewModelScope.launch(dispatcher) {
            progressPercent.set(80)
            try {
                val responseCountries = apiService.getRestCountries()
                if (responseCountries.isSuccessful) {
                    restCountries.value = responseCountries.body()
                    progress.value=false
                } else progress.value=false

            } catch (e: Exception) {
                progress.value=false
                Log.d("Error", "Api error" + e.message)
            }

        }

    }

    fun fetchRecords(str:String){
        viewModelScope.launch(dispatcher) {
            countiesDbData.value = dbHelper.getSearchCountries(str)
        }

    }

    fun fetchRecords() {
        viewModelScope.launch(dispatcher) {
            countiesDbData.value = dbHelper.getCountries()
        }
    }
}