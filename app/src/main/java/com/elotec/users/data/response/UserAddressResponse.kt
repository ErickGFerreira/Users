package com.elotec.users.data.respone

import com.elotec.users.data.response.GeolocalizationResponse
import com.google.gson.annotations.SerializedName

data class UserAddressResponse (
    @SerializedName("street") val street: String,
    @SerializedName("suite")val suite: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipcode") val zipcode: String,
    @SerializedName("geo") val geo: GeolocalizationResponse,
)