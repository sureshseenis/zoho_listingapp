package com.zoho.listingapp.activities.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoho.listingapp.api.services.ApiService
import com.zoho.listingapp.room.Countries
import com.zoho.listingapp.room.DatabaseHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : ViewModel() {

    var singleCountry = MutableLiveData<Countries>()
    lateinit var dbHelper: DatabaseHelper


    fun getSingleCountry(id: Long) {
        viewModelScope.launch(dispatcher) {
            singleCountry.value = dbHelper.getSingleCountry(id)
        }

    }
}