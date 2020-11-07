package com.zoho.listingapp.di.coponent

import com.zoho.listingapp.di.factory.ViewModelFactory
import com.zoho.listingapp.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(viewModelFactory: ViewModelFactory)
}