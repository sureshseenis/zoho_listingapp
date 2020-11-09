package com.zoho.listingapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountriesDao {

    @Query("SELECT * FROM Countries")
    suspend fun getAll(): List<Countries>

    @Insert
    suspend fun insertAll(users: List<Countries>)

    @Delete
    suspend fun delete(user: Countries)

    @Query("DELETE FROM Countries")
    fun deleteCountries()

    @Query("SELECT * FROM Countries WHERE name LIKE :searchText")
    suspend fun getSearchCountries(searchText:String): List<Countries>

    @Query("SELECT * FROM Countries WHERE id= :id_no")
    suspend fun getSingleCountry(id_no:Long): Countries
}