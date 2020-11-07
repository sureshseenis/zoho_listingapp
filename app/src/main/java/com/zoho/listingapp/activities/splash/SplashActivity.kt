package com.zoho.listingapp.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zoho.listingapp.R
import com.zoho.listingapp.activities.listing.ListingActivity
import com.zoho.listingapp.databinding.ActivitySplashBinding
import com.zoho.listingapp.di.factory.ViewModelFactory

class SplashActivity : AppCompatActivity() {
    lateinit var splashActivityDataBinding: ActivitySplashBinding
    lateinit var viewModelSplashActivity: SplashActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashActivityDataBinding =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        //viewModel providers
        val viewModelFactory = ViewModelFactory()
        viewModelSplashActivity =
            ViewModelProvider(this, viewModelFactory).get(SplashActivityViewModel::class.java)
        splashActivityDataBinding.viewModel = viewModelSplashActivity

        splashActivityDataBinding.ivSplash.setOnClickListener{
            startActivity(Intent(this,ListingActivity::class.java))
        }

    }
}