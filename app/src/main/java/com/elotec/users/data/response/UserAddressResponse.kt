package com.elotec.users.data.respone

import com.elotec.users.data.response.GeolocalizationResponse
import com.elotec.users.domain.model.Geolocalization
import com.elotec.users.domain.model.UserAddress
import com.google.gson.annotations.SerializedName

data class UserAddressResponse (
    @SerializedName("street") val street: String,
    @SerializedName("suite")val suite: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipcode") val zipcode: String,
    @SerializedName("geo") val geo: GeolocalizationResponse,
){
    companion object {
        fun mockUserAddress() = UserAddressResponse(
            street = "Av. Paulista",
            suite = "Apt. 2",
            city = "Cariacica",
            zipcode = "29.141-561",
            geo = GeolocalizationResponse.mockGeo()
        )
    }
}