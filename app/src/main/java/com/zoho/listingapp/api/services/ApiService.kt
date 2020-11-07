package com.zoho.listingapp.api.services

import com.zoho.listingapp.api.models.RestCountries
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("rest/v2/all ")
   suspend fun getRestCountries(): Response<List<RestCountries.RestCountriesItem>>
}