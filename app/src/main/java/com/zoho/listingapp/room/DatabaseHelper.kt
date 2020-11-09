package com.zoho.listingapp.room

interface DatabaseHelper {

    suspend fun getCountries(): List<Countries>

    suspend fun insertAll(countries: List<Countries>)

    suspend fun deleteCountries()

    suspend fun getSearchCountries(str: String): List<Countries>

    suspend fun getSingleCountry(id: Long): Countries

}