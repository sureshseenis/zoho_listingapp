package com.zoho.listingapp.activities.listing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zoho.listingapp.R
import com.zoho.listingapp.adapters.CountriesListAdapter
import com.zoho.listingapp.databinding.ActivityListingBinding
import com.zoho.listingapp.di.factory.ViewModelFactory

class ListingActivity : AppCompatActivity() {
    lateinit var listingBinding: ActivityListingBinding
    lateinit var listingViewModel: ListingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listingBinding = DataBindingUtil.setContentView(this, R.layout.activity_listing)

        //ViewModel provider
        val viewModelFactory = ViewModelFactory()
        listingViewModel =
            ViewModelProvider(this, viewModelFactory).get(ListingActivityViewModel::class.java)

        listingViewModel.getCountriesFromApi()

        listingViewModel.restCountries.observe(this, Observer {
            listingBinding.rvCountries.adapter = CountriesListAdapter(this, it)
        })

    }
}