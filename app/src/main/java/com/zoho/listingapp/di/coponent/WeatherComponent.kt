package com.zoho.listingapp.di.coponent

import com.zoho.listingapp.di.factory.ViewModelFactory
import com.zoho.listingapp.di.factory.WeatherViewModelFactory
import com.zoho.listingapp.di.module.NetworkModule
import com.zoho.listingapp.di.module.WeatherModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [WeatherModule::class])
interface WeatherComponent {
    fun inject(viewModelFactory: WeatherViewModelFactory)
}