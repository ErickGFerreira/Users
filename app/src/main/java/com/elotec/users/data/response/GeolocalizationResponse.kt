package com.elotec.users.data.response

import com.google.gson.annotations.SerializedName

data class GeolocalizationResponse(
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lgn: String,
)