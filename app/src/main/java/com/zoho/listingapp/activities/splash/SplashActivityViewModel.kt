package com.zoho.listingapp.activities.splash

import androidx.lifecycle.ViewModel
import com.zoho.listingapp.api.services.ApiService
import kotlinx.coroutines.CoroutineDispatcher

class SplashActivityViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : ViewModel() {
}