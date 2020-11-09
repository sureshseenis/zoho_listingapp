package com.zoho.listingapp.room

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getCountries(): List<Countries> = appDatabase.getCountriesDao().getAll()

    override suspend fun insertAll(countries: List<Countries>) =
        appDatabase.getCountriesDao().insertAll(countries)

    override suspend fun deleteCountries() {
        appDatabase.getCountriesDao().deleteCountries()
    }

    override suspend fun getSearchCountries(str: String):List<Countries>{
        return appDatabase.getCountriesDao().getSearchCountries(str)
    }

    override suspend fun getSingleCountry(id: Long):Countries{
        return appDatabase.getCountriesDao().getSingleCountry(id)
    }


}