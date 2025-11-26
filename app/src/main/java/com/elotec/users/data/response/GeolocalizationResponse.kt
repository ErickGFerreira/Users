package com.elotec.users.data.response

import com.google.gson.annotations.SerializedName

data class GeolocalizationResponse(
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
){
    companion object {
        fun mockGeo() = GeolocalizationResponse(
            lat = "12.4",
            lng = "12.2"
        )
    }
}