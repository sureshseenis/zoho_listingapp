package com.zoho.listingapp.api.services

import com.zoho.listingapp.api.models.RestCountries
import com.zoho.listingapp.api.models.WeatherResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("rest/v2/all ")
    suspend fun getRestCountries(): Response<List<RestCountries.RestCountriesItem>>

    @GET("v1/current.json")
    suspend fun getWeatherDetails(
        @Query("key") key: String,
        @Query("q") q: String
    ): Response<WeatherResponse>
}