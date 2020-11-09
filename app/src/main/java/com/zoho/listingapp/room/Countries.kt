package com.zoho.listingapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Countries(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "callingCodes") val callingCodes: String?,
    @ColumnInfo(name = "region") val region: String?,
    @ColumnInfo(name = "flag") val flag: String?,
    @ColumnInfo(name = "subregion") val subregion: String?

)