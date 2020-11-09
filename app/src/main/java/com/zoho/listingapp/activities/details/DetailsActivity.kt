package com.zoho.listingapp.activities.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahmadrosid.svgloader.SvgLoader
import com.zoho.listingapp.R
import com.zoho.listingapp.databinding.ActivityDetailsBinding
import com.zoho.listingapp.di.factory.ViewModelFactory
import com.zoho.listingapp.room.DatabaseBuilder
import com.zoho.listingapp.room.DatabaseHelperImpl
import kotlinx.android.synthetic.main.row_country_listing_item.view.*

class DetailsActivity : AppCompatActivity() {

    lateinit var detailsBinding: ActivityDetailsBinding
    lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Room Database initiated
        var dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))

        detailsBinding =
            DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
        val viewModelFactory = ViewModelFactory()
        detailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)

        detailsViewModel.dbHelper = dbHelper
        if (intent.hasExtra("id")) {
            detailsViewModel.getSingleCountry(intent.getLongExtra("id", 0))
        }

        detailsBinding.ivBack.setOnClickListener {
            finish()
        }

        detailsViewModel.singleCountry.observe(this, Observer {
            detailsBinding.tvCountryCode.text = it.callingCodes
            detailsBinding.tvCountryName.text = it.name
            detailsBinding.tvRegion.text = it.region
            detailsBinding.tvSubRegion.text = it.subregion

            SvgLoader.pluck()
                .with(this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(it.flag, detailsBinding.ivCountry)
        })


    }
}