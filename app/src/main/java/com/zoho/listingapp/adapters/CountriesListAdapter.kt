package com.zoho.listingapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.zoho.listingapp.R
import com.zoho.listingapp.activities.details.DetailsActivity
import com.zoho.listingapp.api.models.RestCountries
import com.zoho.listingapp.room.Countries
import kotlinx.android.synthetic.main.row_country_listing_item.view.*


class CountriesListAdapter(val ctx: Activity, private val list: List<Countries>) :
    RecyclerView.Adapter<CountriesListAdapter.MainActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_country_listing_item, null)
        return MainActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.itemView.tv_country.text = list[position].name

        SvgLoader.pluck()
            .with(ctx)
            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
            .load(list[position].flag, holder.itemView.iv_country)

        holder.itemView.view_parent.setOnClickListener {
            ctx.startActivity(
                Intent(ctx, DetailsActivity::class.java).putExtra(
                    "id",
                    list[position].id
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}