package com.elotec.users.domain.model

import com.elotec.users.utils.EMPTY_STRING
import java.io.Serializable

data class Geolocalization(
    val lat: String = EMPTY_STRING,
    val lng: String = EMPTY_STRING,
) : Serializable {
    companion object {
        fun mockGeo() = Geolocalization(
            lat = "12.4",
            lng = "12.2"
        )
    }
}