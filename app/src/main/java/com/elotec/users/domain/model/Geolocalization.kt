package com.elotec.users.domain.model

data class Geolocalization(
    val lat: String,
    val lgn: String,
){
    companion object{
        fun mockGeo() = Geolocalization(
            lat = "12.4",
            lgn = "12.2"
        )
    }
}