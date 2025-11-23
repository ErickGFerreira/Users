package com.elotec.users.domain.model

data class UserAddress(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geolocalization,
){
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