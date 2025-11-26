package com.elotec.users.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elotec.users.data.respone.UserAddressResponse
import com.elotec.users.data.response.GeolocalizationResponse
import com.elotec.users.domain.model.Company
import com.elotec.users.domain.model.Geolocalization
import com.elotec.users.domain.model.User
import com.elotec.users.domain.model.UserAddress

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    @Embedded(prefix = "address_")
    val address: AddressEntity,
    @Embedded(prefix = "company_")
    val company: CompanyEntity
) {
    companion object {
        fun mockUser() = UserEntity(
            id = 1,
            name = "Carlos",
            username = "Carlitos",
            email = "carlosnogueira@gmail.com",
            address = AddressEntity.mockUserAddress(),
            phone = "27999999999",
            website = "https://www.carlitos.com",
            company = CompanyEntity.mockCompany()
        )

        fun mockList() = listOf(
            mockUser(),
            mockUser().copy(id = 2, name = "Erick", email = "erick@gmail.com"),
            mockUser().copy(id = 3, name = "Glauber", email = "glauber@gmail.com"),
            mockUser().copy(id = 4, name = "Samara", email = "samara@gmail.com"),
            mockUser().copy(id = 5, name = "Lorena", email = "lorena@gmail.com"),
            mockUser().copy(id = 6, name = "Mila", email = "mila@gmail.com"),
        )
    }
}

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded(prefix = "geo_")
    val geo: GeoEntity
) {
    companion object {
        fun mockUserAddress() = AddressEntity(
            street = "Av. Paulista",
            suite = "Apt. 2",
            city = "Cariacica",
            zipcode = "29.141-561",
            geo = GeoEntity.mockGeo()
        )
    }
}

data class GeoEntity(
    val lat: String,
    val lng: String
) {
    companion object {
        fun mockGeo() = GeoEntity(
            lat = "12.4",
            lng = "12.2"
        )
    }
}

data class CompanyEntity(
    val name: String,
    val catchPhrase: String,
    val bs: String
) {
    companion object {
        fun mockCompany() = CompanyEntity(
            name = "Temple Co.",
            catchPhrase = "Technology and Solutions",
            bs = "change customers life"
        )
    }
}