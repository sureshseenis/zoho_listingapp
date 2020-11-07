package com.zoho.listingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zoho.listingapp.R
import com.zoho.listingapp.api.models.RestCountries
import kotlinx.android.synthetic.main.row_country_listing_item.view.*

class CountriesListAdapter(val ctx : Context, private val list: List<RestCountries.RestCountriesItem>) :
    RecyclerView.Adapter<CountriesListAdapter.MainActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_country_listing_item, null)
        return MainActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.itemView.tv_country.text = list[position].name
        Glide.with(ctx)
            .load(list[position].flag)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.itemView.iv_country);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}