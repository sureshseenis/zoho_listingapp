package com.zoho.listingapp.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoho.listingapp.activities.listing.ListingActivityViewModel
import com.zoho.listingapp.activities.splash.SplashActivityViewModel
import com.zoho.listingapp.api.services.ApiService
import com.zoho.listingapp.di.coponent.DaggerNetworkComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var retrofit: Retrofit

    lateinit var apiService: ApiService

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        DaggerNetworkComponent.create().inject(this)
        //create retrofit service
        apiService = retrofit.create(ApiService::class.java)

        if (modelClass.isAssignableFrom(SplashActivityViewModel::class.java)) {
            return SplashActivityViewModel(
                Dispatchers.Main,
                apiService
            ) as T
        }

        if (modelClass.isAssignableFrom(ListingActivityViewModel::class.java)) {
            return ListingActivityViewModel(
                Dispatchers.Main,
                apiService
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}