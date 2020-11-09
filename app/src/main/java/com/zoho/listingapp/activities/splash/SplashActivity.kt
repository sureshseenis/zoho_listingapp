package com.zoho.listingapp.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zoho.listingapp.R
import com.zoho.listingapp.activities.listing.ListingActivity
import com.zoho.listingapp.databinding.ActivitySplashBinding
import com.zoho.listingapp.di.factory.ViewModelFactory
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private lateinit var splashActivityDataBinding: ActivitySplashBinding
    private lateinit var viewModelSplashActivity: SplashActivityViewModel
    private val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashActivityDataBinding =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        //viewModel providers
        val viewModelFactory = ViewModelFactory()
        viewModelSplashActivity =
            ViewModelProvider(this, viewModelFactory).get(SplashActivityViewModel::class.java)

        splashActivityDataBinding.viewModel = viewModelSplashActivity

        activityScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, ListingActivity::class.java))
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
       // activityScope.cancel()
        super.onPause()
    }
}