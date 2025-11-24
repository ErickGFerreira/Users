package com.elotec.users.data.mapper

import com.elotec.users.data.respone.UserAddressResponse
import com.elotec.users.data.response.CompanyResponse
import com.elotec.users.data.response.GeolocalizationResponse
import com.elotec.users.data.response.UserResponse
import com.elotec.users.domain.model.Company
import com.elotec.users.domain.model.Geolocalization
import com.elotec.users.domain.model.User
import com.elotec.users.domain.model.UserAddress

object UserDataMapper {
    fun UserResponse.toDomain() =
        User(
            id = id,
            name = name,
            username = username,
            email = email,
            address = address.toDomain(),
            phone = phone,
            website = website,
            company = company.toDomain()
        )

    fun UserAddressResponse.toDomain() =
        UserAddress(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode,
            geo = geo.toDomain()
        )

    fun GeolocalizationResponse.toDomain() =
        Geolocalization(
            lat = lat,
            lgn = lgn,
        )

    fun CompanyResponse.toDomain() = Company(
        name = name,
        catchPhrase = catchPhrase,
        bs = bs
    )
}