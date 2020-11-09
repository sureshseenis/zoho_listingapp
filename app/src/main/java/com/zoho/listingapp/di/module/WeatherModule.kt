package com.zoho.listingapp.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class WeatherModule() {

    @Provides
    fun provideWeatherRetroInfo(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.weatherapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}