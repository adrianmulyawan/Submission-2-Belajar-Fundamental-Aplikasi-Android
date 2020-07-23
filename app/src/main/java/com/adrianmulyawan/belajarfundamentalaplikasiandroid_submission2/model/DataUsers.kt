package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUsers(
    var username: String? ="",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable