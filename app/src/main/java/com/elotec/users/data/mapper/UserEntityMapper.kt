package com.elotec.users.data.mapper

import com.elotec.users.data.local.entity.AddressEntity
import com.elotec.users.data.local.entity.CompanyEntity
import com.elotec.users.data.local.entity.GeoEntity
import com.elotec.users.data.local.entity.UserEntity
import com.elotec.users.domain.model.Company
import com.elotec.users.domain.model.Geolocalization
import com.elotec.users.domain.model.User
import com.elotec.users.domain.model.UserAddress

object UserEntityMapper {

    fun User.toEntity(): UserEntity = UserEntity(
        id = id,
        name = name,
        username = username,
        email = email,
        phone = phone,
        website = website,
        address = address.toEntity(),
        company = company.toEntity()
    )

    fun UserEntity.toDomain(): User = User(
        id = id,
        name = name,
        username = username,
        email = email,
        phone = phone,
        website = website,
        address = address.toDomain(),
        company = company.toDomain()
    )

    private fun UserAddress.toEntity(): AddressEntity = AddressEntity(
        street = street,
        suite = suite,
        city = city,
        zipcode = zipcode,
        geo = geo.toEntity()
    )

    private fun AddressEntity.toDomain(): UserAddress = UserAddress(
        street = street,
        suite = suite,
        city = city,
        zipcode = zipcode,
        geo = geo.toDomain()
    )

    private fun Geolocalization.toEntity(): GeoEntity = GeoEntity(
        lat = lat,
        lng = lgn
    )

    private fun GeoEntity.toDomain(): Geolocalization = Geolocalization(
        lat = lat,
        lgn = lng
    )

    private fun Company.toEntity(): CompanyEntity = CompanyEntity(
        name = name,
        catchPhrase = catchPhrase,
        bs = bs
    )

    private fun CompanyEntity.toDomain(): Company = Company(
        name = name,
        catchPhrase = catchPhrase,
        bs = bs
    )
}