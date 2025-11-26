package com.elotec.users.domain.model

import com.elotec.users.utils.EMPTY_STRING
import java.io.Serializable


data class UserAddress(
    val street: String = EMPTY_STRING,
    val suite: String = EMPTY_STRING,
    val city: String = EMPTY_STRING,
    val zipcode: String = EMPTY_STRING,
    val geo: Geolocalization = Geolocalization(),
) : Serializable {
    companion object {
        fun mockUserAddress() = UserAddress(
            street = "Av. Paulista",
            suite = "Apt. 2",
            city = "Cariacica",
            zipcode = "29.141-561",
            geo = Geolocalization.mockGeo()
        )
    }
}