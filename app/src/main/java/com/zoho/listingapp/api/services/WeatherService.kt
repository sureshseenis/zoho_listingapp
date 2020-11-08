package com.zoho.listingapp.api.services

import com.zoho.listingapp.api.models.RestCountries
import com.zoho.listingapp.api.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v1/current.json")
    suspend fun getWeatherDetails(
        @Query("key") key: String,
        @Query("q") q: String
    ): Response<WeatherResponse>
}