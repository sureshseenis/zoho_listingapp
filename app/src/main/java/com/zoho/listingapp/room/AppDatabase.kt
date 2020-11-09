package com.zoho.listingapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Countries::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCountriesDao(): CountriesDao

}